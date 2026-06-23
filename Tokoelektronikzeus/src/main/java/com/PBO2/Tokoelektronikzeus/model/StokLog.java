package com.PBO2.Tokoelektronikzeus.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stok_log")
public class StokLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kode_barang", nullable = false)
    private Barang barang;

    @Enumerated(EnumType.STRING)
    @Column(name = "jenis_mutasi", nullable = false)
    private JenisMutasi jenisMutasi;

    @Column(name = "jumlah", nullable = false)
    private int jumlah;

    @Column(name = "stok_sebelum", nullable = false)
    private int stokSebelum;

    @Column(name = "stok_sesudah", nullable = false)
    private int stokSesudah;

    @Column(name = "no_transaksi", length = 20)
    private String noTransaksi;

    @Column(name = "tanggal", nullable = false)
    private LocalDateTime tanggal;

    @Column(name = "keterangan", length = 255)
    private String keterangan;

    public enum JenisMutasi {
        MASUK, KELUAR
    }

    public StokLog() {

    }

    public StokLog(Barang barang, JenisMutasi jenisMutasi, int jumlah,
                   int stokSebelum, int stokSesudah, String noTransaksi, String keterangan) {
        this.barang = barang;
        this.jenisMutasi = jenisMutasi;
        this.jumlah = jumlah;
        this.stokSebelum = stokSebelum;
        this.stokSesudah = stokSesudah;
        this.noTransaksi = noTransaksi;
        this.tanggal = LocalDateTime.now();
        this.keterangan = keterangan;
    }

    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }

    public Barang getBarang() { 
        return barang; 
    }

    public void setBarang(Barang barang) { 
        this.barang = barang; 
    }

    public JenisMutasi getJenisMutasi() { 
        return jenisMutasi; 
    }

    public void setJenisMutasi(JenisMutasi jenisMutasi) { 
        this.jenisMutasi = jenisMutasi; 
    }

    public int getJumlah() { 
        return jumlah; 
    }

    public void setJumlah(int jumlah) { 
        this.jumlah = jumlah; 
    }

    public int getStokSebelum() { 
        return stokSebelum; 
    }

    public void setStokSebelum(int stokSebelum) { 
        this.stokSebelum = stokSebelum; 
    }

    public int getStokSesudah() { 
        return stokSesudah; 
    }

    public void setStokSesudah(int stokSesudah) {
        this.stokSesudah = stokSesudah; 
    }

    public String getNoTransaksi() { 
        return noTransaksi; 
    }

    public void setNoTransaksi(String noTransaksi) { 
        this.noTransaksi = noTransaksi; 
    }

    public LocalDateTime getTanggal() { 
        return tanggal; 
    }
    public void setTanggal(LocalDateTime tanggal) {
        this.tanggal = tanggal; 
    }

    public String getKeterangan() { 
        return keterangan; 
    }
    
    public void setKeterangan(String keterangan) { 
        this.keterangan = keterangan; 
    }
}