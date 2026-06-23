package com.PBO2.Tokoelektronikzeus.repository;

import com.PBO2.Tokoelektronikzeus.model.Penjualan;
import com.PBO2.Tokoelektronikzeus.model.Penjualan.JenisTransaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PenjualanRepository extends JpaRepository<Penjualan, String> {

    List<Penjualan> findByJenisTransaksi(JenisTransaksi jenisTransaksi);

    List<Penjualan> findByTanggalTransaksiBetween(LocalDateTime dari, LocalDateTime sampai);


    List<Penjualan> findByJenisTransaksiAndTanggalTransaksiBetween(
            JenisTransaksi jenisTransaksi, LocalDateTime dari, LocalDateTime sampai);

    @Query(value = "SELECT no_transaksi FROM penjualan WHERE no_transaksi LIKE :prefix ORDER BY no_transaksi DESC LIMIT 1", nativeQuery = true)
String findLatestNoTransaksiByPrefix(@Param("prefix") String prefix);
}