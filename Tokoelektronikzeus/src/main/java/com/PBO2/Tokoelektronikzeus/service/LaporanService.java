package com.PBO2.Tokoelektronikzeus.service;

import com.PBO2.Tokoelektronikzeus.model.StokLog;
import com.PBO2.Tokoelektronikzeus.model.StokLog.JenisMutasi;
import com.PBO2.Tokoelektronikzeus.repository.StokLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LaporanService {

    private final StokLogRepository stokLogRepository;

    public LaporanService(StokLogRepository stokLogRepository) {
        this.stokLogRepository = stokLogRepository;
    }

    public List<StokLog> getAllLog() {
        return stokLogRepository.findAll();
    }

    public List<StokLog> getLogMasuk() {
        return stokLogRepository.findByJenisMutasi(JenisMutasi.MASUK);
    }

    public List<StokLog> getLogKeluar() {
        return stokLogRepository.findByJenisMutasi(JenisMutasi.KELUAR);
    }

    public List<StokLog> getLogByBarang(String kodeBarang) {
        return stokLogRepository.findByKodeBarangRef(kodeBarang);
    }

    public List<StokLog> getLogByPeriode(LocalDate dari, LocalDate sampai) {
        LocalDateTime start = dari.atStartOfDay();
        LocalDateTime end = sampai.atTime(23, 59, 59);
        return stokLogRepository.findByTanggalBetween(start, end);
    }

    public List<StokLog> getLogByBarangDanPeriode(String kodeBarang, LocalDate dari, LocalDate sampai) {
        LocalDateTime start = dari.atStartOfDay();
        LocalDateTime end = sampai.atTime(23, 59, 59);
        return stokLogRepository.findByKodeBarangRefAndTanggalBetween(kodeBarang, start, end);
    }
}
