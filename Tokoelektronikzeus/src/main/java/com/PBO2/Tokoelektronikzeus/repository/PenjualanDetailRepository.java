package com.PBO2.Tokoelektronikzeus.repository;

import com.PBO2.Tokoelektronikzeus.model.PenjualanDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PenjualanDetailRepository extends JpaRepository<PenjualanDetail, Long> {
    List<PenjualanDetail> findByPenjualan_NoTransaksi(String noTransaksi);
}