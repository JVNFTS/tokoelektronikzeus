package com.PBO2.Tokoelektronikzeus.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_customer", length = 100, nullable = false)
    private String namaCustomer;

    @Column(name = "no_telepon", length = 15)
    private String noTelepon;

    @Column(name = "alamat", length = 255)
    private String alamat;

    @OneToMany(mappedBy = "customer")
    private List<Penjualan> penjualanList;

    public Customer() {}

    public Customer(String namaCustomer, String noTelepon, String alamat) {
        this.namaCustomer = namaCustomer;
        this.noTelepon = noTelepon;
        this.alamat = alamat;
    }

    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }

    public String getNamaCustomer() {
        return namaCustomer; 
    }

    public void setNamaCustomer(String namaCustomer) { 
        this.namaCustomer = namaCustomer; 
    }

    public String getNoTelepon() { 
        return noTelepon; 
    }
    
    public void setNoTelepon(String noTelepon) { 
        this.noTelepon = noTelepon; 
    }

    public String getAlamat() { 
        return alamat; 
    }

    public void setAlamat(String alamat) { 
        this.alamat = alamat; 
    }

    public List<Penjualan> getPenjualanList() { 
        return penjualanList; 
    }

    public void setPenjualanList(List<Penjualan> penjualanList) { 
        this.penjualanList = penjualanList; 
    }
}
