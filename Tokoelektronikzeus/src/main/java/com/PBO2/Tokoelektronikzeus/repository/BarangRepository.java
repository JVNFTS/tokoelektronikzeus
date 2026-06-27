package com.PBO2.Tokoelektronikzeus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.PBO2.Tokoelektronikzeus.model.Barang;

import java.util.List;

public interface BarangRepository extends JpaRepository<Barang, String> {

    List<Barang> findByAktifTrue();
}
