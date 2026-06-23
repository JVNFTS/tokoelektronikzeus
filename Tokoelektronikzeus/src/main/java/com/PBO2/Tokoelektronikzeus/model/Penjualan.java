package com.PBO2.Tokoelektronikzeus.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "penjualan")
public class Penjualan {

    @Id
    @Column(name = "no_transaksi", length = 20)
    private String noTransaksi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "tanggal_transaksi", nullable = false)
    private LocalDateTime tanggalTransaksi;

    @Enumerated(EnumType.STRING)
    @Column(name = "jenis_transaksi", nullable = false)
    private JenisTransaksi jenisTransaksi;

    @Column(name = "total_harga", nullable = false)
    private long totalHarga;

    @Column(name = "keterangan", length = 255)
    private String keterangan;

    @OneToMany(mappedBy = "penjualan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PenjualanDetail> detailList;

    public enum JenisTransaksi {
        JUAL, RESTOCK
    }

    public Penjualan() {}

    public Penjualan(String noTransaksi, Customer customer, User user,
                     LocalDateTime tanggalTransaksi, JenisTransaksi jenisTransaksi,
                     long totalHarga, String keterangan) {
        this.noTransaksi = noTransaksi;
        this.customer = customer;
        this.user = user;
        this.tanggalTransaksi = tanggalTransaksi;
        this.jenisTransaksi = jenisTransaksi;
        this.totalHarga = totalHarga;
        this.keterangan = keterangan;
    }

    public String getNoTransaksi() { 
        return noTransaksi; 
    }

    public void setNoTransaksi(String noTransaksi) { 
        this.noTransaksi = noTransaksi; 
    }

    public Customer getCustomer() { 
        return customer; 
    }

    public void setCustomer(Customer customer) { 
        this.customer = customer; 
    }

    public User getUser() { 
        return user; 
    }
    public void setUser(User user) {
        this.user = user; 
    }

    public LocalDateTime getTanggalTransaksi() { 
        return tanggalTransaksi; 
    }

    public void setTanggalTransaksi(LocalDateTime tanggalTransaksi) { 
        this.tanggalTransaksi = tanggalTransaksi; 
    }

    public JenisTransaksi getJenisTransaksi() { 
        return jenisTransaksi; 
    }

    public void setJenisTransaksi(JenisTransaksi jenisTransaksi) { 
        this.jenisTransaksi = jenisTransaksi; 
    }

    public long getTotalHarga() { 
        return totalHarga; 
    }

    public void setTotalHarga(long totalHarga) { 
        this.totalHarga = totalHarga; 
    }

    public String getKeterangan() { 
        return keterangan; 
    }

    public void setKeterangan(String keterangan) { 
        this.keterangan = keterangan; 
    }

    public List<PenjualanDetail> getDetailList() { 
        return detailList; 
    }

    public void setDetailList(List<PenjualanDetail> detailList) { 
        this.detailList = detailList;
    }
}
