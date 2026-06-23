package com.PBO2.Tokoelektronikzeus.service;

import com.PBO2.Tokoelektronikzeus.model.*;
import com.PBO2.Tokoelektronikzeus.model.Penjualan.JenisTransaksi;
import com.PBO2.Tokoelektronikzeus.model.StokLog.JenisMutasi;
import com.PBO2.Tokoelektronikzeus.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TransaksiService {

    private final PenjualanRepository penjualanRepository;
    private final PenjualanDetailRepository detailRepository;
    private final BarangRepository barangRepository;
    private final StokLogRepository stokLogRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    public TransaksiService(PenjualanRepository penjualanRepository,
                            PenjualanDetailRepository detailRepository,
                            BarangRepository barangRepository,
                            StokLogRepository stokLogRepository,
                            UserRepository userRepository,
                            CustomerRepository customerRepository) {
        this.penjualanRepository = penjualanRepository;
        this.detailRepository = detailRepository;
        this.barangRepository = barangRepository;
        this.stokLogRepository = stokLogRepository;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }

    public String generateNoTransaksi(JenisTransaksi jenis) {
        String prefix = jenis == JenisTransaksi.JUAL ? "JL" : "RS";
        String tanggal = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String prefixFull = prefix + tanggal;

        String latest = penjualanRepository.findLatestNoTransaksiByPrefix(prefixFull + "%");

        int urutan = 1;
        if (latest != null && latest.startsWith(prefixFull)) {
            try {
                urutan = Integer.parseInt(latest.substring(prefixFull.length())) + 1;
            } catch (NumberFormatException ignored) {}
        }
        return String.format("%s%03d", prefixFull, urutan);
    }

    @Transactional
    public void simpanTransaksi(Penjualan penjualan, List<PenjualanDetail> details) {
        double total = 0;

        for (PenjualanDetail detail : details) {
            Barang barang = barangRepository.findById(detail.getBarang().getKodeBarang())
                    .orElseThrow(() -> new RuntimeException("Barang tidak ditemukan: "
                            + detail.getBarang().getKodeBarang()));

            int stokSebelum = barang.getStok();
            int jumlah = detail.getJumlah();

            if (barang.getStok() < jumlah) {
                throw new RuntimeException("Stok tidak cukup untuk: " + barang.getNamaBarang()
                        + " (stok: " + stokSebelum + ", diminta: " + jumlah + ")");
            }
            barang.setStok(stokSebelum - jumlah);
            barangRepository.save(barang);

            stokLogRepository.save(new StokLog(
                    barang, JenisMutasi.KELUAR, jumlah, stokSebelum, barang.getStok(),
                    penjualan.getNoTransaksi(), "JUAL"));

            detail.setPenjualan(penjualan);
            detail.setHargaSatuan(barang.getHarga());
            detail.setSubtotal(jumlah * barang.getHarga());
            total += detail.getSubtotal();
        }

        penjualan.setTotalHarga(total);
        penjualan.setTanggalTransaksi(LocalDateTime.now());
        penjualanRepository.save(penjualan);
        detailRepository.saveAll(details);
    }

    @Transactional
    public void simpanRestock(Penjualan penjualan, List<PenjualanDetail> details) {
        for (PenjualanDetail detail : details) {
            Barang barang = barangRepository.findById(detail.getBarang().getKodeBarang())
                    .orElseThrow(() -> new RuntimeException("Kode barang tidak ditemukan: "
                            + detail.getBarang().getKodeBarang()));

            int stokSebelum = barang.getStok();
            int jumlah = detail.getJumlah();
            barang.setStok(stokSebelum + jumlah);
            barangRepository.save(barang);

            stokLogRepository.save(new StokLog(
                    barang, JenisMutasi.MASUK, jumlah, stokSebelum, barang.getStok(),
                    penjualan.getNoTransaksi(), "RESTOCK"));

            detail.setPenjualan(penjualan);
            detail.setHargaSatuan(0);
            detail.setSubtotal(0);
        }

        penjualan.setTotalHarga(0);
        penjualan.setTanggalTransaksi(LocalDateTime.now());
        penjualan.setJenisTransaksi(JenisTransaksi.RESTOCK);
        penjualanRepository.save(penjualan);
        detailRepository.saveAll(details);
    }

    @Transactional
    public void hapusTransaksi(String noTransaksi) {
        Penjualan penjualan = penjualanRepository.findById(noTransaksi)
                .orElseThrow(() -> new RuntimeException("Transaksi tidak ditemukan: " + noTransaksi));

        List<PenjualanDetail> details = detailRepository.findByPenjualan_NoTransaksi(noTransaksi);

        for (PenjualanDetail detail : details) {
            Barang barang = barangRepository.findById(detail.getBarang().getKodeBarang())
                    .orElse(null);

            if (barang != null) {
                int stokSekarang = barang.getStok();
                int jumlah = detail.getJumlah();

                if (penjualan.getJenisTransaksi() == JenisTransaksi.JUAL) {
                    barang.setStok(stokSekarang + jumlah);
                } else {
                    barang.setStok(Math.max(0, stokSekarang - jumlah));
                }
                barangRepository.save(barang);
            }
        }

        stokLogRepository.deleteByNoTransaksi(noTransaksi);

        detailRepository.deleteAll(details);
        penjualanRepository.delete(penjualan);
    }

    @Transactional
    public Customer simpanCustomerBaru(String nama, String noTelepon, String alamat) {
        Customer c = new Customer(nama, noTelepon, alamat);
        return customerRepository.save(c);
    }

    public List<Penjualan> getAll() {
        return penjualanRepository.findByJenisTransaksi(JenisTransaksi.JUAL);
    }

    public List<Penjualan> getByJenis(JenisTransaksi jenis) {
        return penjualanRepository.findByJenisTransaksi(jenis);
    }

    public Penjualan getByNo(String noTransaksi) {
        return penjualanRepository.findById(noTransaksi).orElse(null);
    }

    public List<Penjualan> getByPeriode(LocalDateTime dari, LocalDateTime sampai) {
        return penjualanRepository.findByTanggalTransaksiBetween(dari, sampai);
    }

    public List<PenjualanDetail> getDetailByNo(String noTransaksi) {
        return detailRepository.findByPenjualan_NoTransaksi(noTransaksi);
    }
}
