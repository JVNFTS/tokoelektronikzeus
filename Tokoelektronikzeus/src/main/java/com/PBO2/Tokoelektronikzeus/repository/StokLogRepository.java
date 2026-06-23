package com.PBO2.Tokoelektronikzeus.repository;

import com.PBO2.Tokoelektronikzeus.model.StokLog;
import com.PBO2.Tokoelektronikzeus.model.StokLog.JenisMutasi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StokLogRepository extends JpaRepository<StokLog, Long> {

    List<StokLog> findByBarang_KodeBarang(String kodeBarang);

    List<StokLog> findByJenisMutasi(JenisMutasi jenisMutasi);

    List<StokLog> findByTanggalBetween(LocalDateTime dari, LocalDateTime sampai);

    List<StokLog> findByBarang_KodeBarangAndTanggalBetween(
            String kodeBarang, LocalDateTime dari, LocalDateTime sampai);

    void deleteByNoTransaksi(String noTransaksi);
}