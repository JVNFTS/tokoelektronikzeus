package com.PBO2.Tokoelektronikzeus.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.PBO2.Tokoelektronikzeus.model.Barang;
import com.PBO2.Tokoelektronikzeus.repository.BarangRepository;

@Service
public class BarangService {

    private final BarangRepository barangRepository;

    public BarangService(BarangRepository barangRepository) {
        this.barangRepository = barangRepository;
    }

    public List<Barang> getAllBarang() {
        return barangRepository.findAll();
    }

    public Barang getBarangByKode(String kodeBarang) {
        return barangRepository.findById(kodeBarang).orElse(null);
    }

    public void tambahBarang(Barang barang) {
        barangRepository.save(barang);
    }

    public void updateBarang(Barang barang) {
        barangRepository.save(barang);
    }

    public void hapusBarang(String kodeBarang) {
        barangRepository.deleteById(kodeBarang);
    }
}
