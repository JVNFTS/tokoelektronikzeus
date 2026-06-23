package com.PBO2.Tokoelektronikzeus.controller;

import com.PBO2.Tokoelektronikzeus.model.StokLog;
import com.PBO2.Tokoelektronikzeus.service.BarangService;
import com.PBO2.Tokoelektronikzeus.service.LaporanService;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/laporan")
public class LaporanController {

    private final LaporanService laporanService;
    private final BarangService barangService;

    public LaporanController(LaporanService laporanService, BarangService barangService) {
        this.laporanService = laporanService;
        this.barangService = barangService;
    }

    private boolean belumLogin(HttpSession session) {
        return session.getAttribute("loggedInUser") == null;
    }

    @GetMapping("/stok")
    public String laporanStok(
            HttpSession session,
            Model model,
            @RequestParam(required = false) String kodeBarang,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dari,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate sampai,
            @RequestParam(required = false) String jenisMutasi) {

        if (belumLogin(session)) return "redirect:/";

        List<StokLog> logs;

        boolean adaFilter = (dari != null && sampai != null);
        boolean adaBarang = (kodeBarang != null && !kodeBarang.isBlank());

        if (adaBarang && adaFilter) {
            logs = laporanService.getLogByBarangDanPeriode(kodeBarang, dari, sampai);
        } else if (adaFilter) {
            logs = laporanService.getLogByPeriode(dari, sampai);
        } else if (adaBarang) {
            logs = laporanService.getLogByBarang(kodeBarang);
        } else if ("MASUK".equals(jenisMutasi)) {
            logs = laporanService.getLogMasuk();
        } else if ("KELUAR".equals(jenisMutasi)) {
            logs = laporanService.getLogKeluar();
        } else {
            logs = laporanService.getAllLog();
        }

        int totalMasuk = logs.stream()
                .filter(l -> l.getJenisMutasi() == StokLog.JenisMutasi.MASUK)
                .mapToInt(StokLog::getJumlah).sum();
        int totalKeluar = logs.stream()
                .filter(l -> l.getJenisMutasi() == StokLog.JenisMutasi.KELUAR)
                .mapToInt(StokLog::getJumlah).sum();

        model.addAttribute("logs", logs);

        model.addAttribute("listBarang", barangService.getAllBarang());

        model.addAttribute("totalMasuk", totalMasuk);

        model.addAttribute("totalKeluar", totalKeluar);

        model.addAttribute("filterKode", kodeBarang);

        model.addAttribute("filterDari", dari);

        model.addAttribute("filterSampai", sampai);
        
        model.addAttribute("filterJenis", jenisMutasi);

        return "laporan/stok";
    }
}
