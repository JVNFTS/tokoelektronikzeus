package com.PBO2.Tokoelektronikzeus.model;

import jakarta.persistence.*;

@Entity
@Table(name = "penjualan_detail")
public class PenjualanDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "no_transaksi", nullable = false)
    private Penjualan penjualan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kode_barang", nullable = false)
    private Barang barang;

    @Column(name = "jumlah", nullable = false)
    private int jumlah;

    @Column(name = "harga_satuan", nullable = false)
    private long hargaSatuan;

    @Column(name = "subtotal", nullable = false)
    private long subtotal;

    public PenjualanDetail() {}

    public PenjualanDetail(Penjualan penjualan, Barang barang, int jumlah, long hargaSatuan) {
        this.penjualan = penjualan;
        this.barang = barang;
        this.jumlah = jumlah;
        this.hargaSatuan = hargaSatuan;
        this.subtotal = jumlah * hargaSatuan;
    }

    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }

    public Penjualan getPenjualan() { 
        return penjualan; 
    }

    public void setPenjualan(Penjualan penjualan) { 
        this.penjualan = penjualan; 
    }

    public Barang getBarang() { 
        return barang; 
    }

    public void setBarang(Barang barang) { 
        this.barang = barang; 
    }

    public int getJumlah() { 
        return jumlah; 
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
        this.subtotal = jumlah * this.hargaSatuan;
    }

    public long getHargaSatuan() { 
        return hargaSatuan; 
    }

    public void setHargaSatuan(long hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
        this.subtotal = this.jumlah * hargaSatuan;
    }

    public long getSubtotal() { 
        return subtotal; 
    }

    public void setSubtotal(long subtotal) { 
        this.subtotal = subtotal; 
    }
}
