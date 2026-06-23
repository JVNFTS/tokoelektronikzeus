package com.PBO2.Tokoelektronikzeus.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "barang")
public class Barang {

    @Id
    @Column(name = "kode_barang", length = 20)
    private String kodeBarang;

    @Column(name = "nama_barang", length = 100, nullable = false)
    private String namaBarang;

    @Column(name = "kategori", length = 50, nullable = false)
    private String kategori;

    @Column(name = "harga", nullable = false)
    private double harga;

    @Column(name = "stok", nullable = false)
    private int stok;

    public Barang() {
    }

    public Barang(String kodeBarang, String namaBarang, String kategori, double harga, int stok) {
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.kategori = kategori;
        this.harga = harga;
        this.stok = stok;
    }

    public String getKodeBarang() { 
        return kodeBarang; 
    }

    public void setKodeBarang(String kodeBarang) { 
        this.kodeBarang = kodeBarang; 
    }

    public String getNamaBarang() { 
        return namaBarang; 
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang; 
        }

    public String getKategori() { 
        return kategori; 
    }

    public void setKategori(String kategori) { 
        this.kategori = kategori; 
    }

    public double getHarga() { 
        return harga; 
    }

    public void setHarga(double harga) { 
        this.harga = harga; 
    }

    public int getStok() { 
        return stok; 
    }

    public void setStok(int stok) { 
        this.stok = stok; 
    }
}
