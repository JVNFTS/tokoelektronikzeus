package com.PBO2.Tokoelektronikzeus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PBO2.Tokoelektronikzeus.model.Barang;
import com.PBO2.Tokoelektronikzeus.repository.BarangRepository;
import com.PBO2.Tokoelektronikzeus.repository.StokLogRepository;

@Service
public class BarangService {

    private final BarangRepository barangRepository;
    private final StokLogRepository stokLogRepository;

    public BarangService(BarangRepository barangRepository, StokLogRepository stokLogRepository) {
        this.barangRepository = barangRepository;
        this.stokLogRepository = stokLogRepository;
    }

    public List<Barang> getAllBarang() {
        return barangRepository.findAll();
    }

    public List<Barang> getBarangAktif() {
        return barangRepository.findByAktifTrue();
    }

    public Barang getBarangByKode(String kodeBarang) {
        return barangRepository.findById(kodeBarang).orElse(null);
    }

    public void tambahBarang(Barang barang) {
        if (barangRepository.existsById(barang.getKodeBarang())) {
            throw new RuntimeException("Kode barang '" + barang.getKodeBarang() + "' sudah digunakan. Gunakan kode yang berbeda.");
        }
        barang.setAktif(true);
        barangRepository.save(barang);
    }

    public void updateBarang(Barang barang) {
        barangRepository.save(barang);
    }

    @Transactional
    public void nonaktifkanBarang(String kodeBarang) {
        Barang barang = barangRepository.findById(kodeBarang)
                .orElseThrow(() -> new RuntimeException("Barang tidak ditemukan: " + kodeBarang));
        barang.setAktif(false);
        barangRepository.save(barang);
    }

    @Transactional
    public void aktifkanBarang(String kodeBarang) {
        Barang barang = barangRepository.findById(kodeBarang)
                .orElseThrow(() -> new RuntimeException("Barang tidak ditemukan: " + kodeBarang));
        barang.setAktif(true);
        barangRepository.save(barang);
    }

    @Transactional
    public void hapusBarang(String kodeBarang) {
        Barang barang = barangRepository.findById(kodeBarang)
                .orElseThrow(() -> new RuntimeException("Barang tidak ditemukan: " + kodeBarang));
        if (barang.isAktif()) {
            throw new RuntimeException("Nonaktifkan barang terlebih dahulu sebelum menghapus.");
        }
        stokLogRepository.detachBarang(kodeBarang);
        barangRepository.delete(barang);
    }
}
