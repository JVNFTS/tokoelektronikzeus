package com.PBO2.Tokoelektronikzeus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.PBO2.Tokoelektronikzeus.model.Barang;
import com.PBO2.Tokoelektronikzeus.service.BarangService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/barang")
public class BarangController {

    private final BarangService barangService;

    public BarangController(BarangService barangService) {
        this.barangService = barangService;
    }

    private boolean belumLogin(HttpSession session) {
        return session.getAttribute("loggedInUser") == null;
    }

    @GetMapping
    public String index(HttpSession session, Model model) {
        if (belumLogin(session)) return "redirect:/";
        model.addAttribute("listBarang", barangService.getAllBarang());
        return "barang/index";
    }

    @GetMapping("/tambah")
    public String tambahForm(HttpSession session, Model model) {
        if (belumLogin(session)) return "redirect:/";
        model.addAttribute("barang", new Barang());
        model.addAttribute("mode", "tambah");
        return "barang/form";
    }

    @PostMapping("/simpan")
    public String simpan(@ModelAttribute Barang barang, HttpSession session, RedirectAttributes ra) {
        if (belumLogin(session)) return "redirect:/";
        try {
            barangService.tambahBarang(barang);
            ra.addFlashAttribute("sukses", "Barang berhasil ditambahkan.");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/barang/tambah";
        }
        return "redirect:/barang";
    }

    @GetMapping("/edit/{kodeBarang}")
    public String editForm(@PathVariable String kodeBarang, HttpSession session, Model model) {
        if (belumLogin(session)) return "redirect:/";
        model.addAttribute("barang", barangService.getBarangByKode(kodeBarang));
        model.addAttribute("mode", "edit");
        return "barang/form";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Barang barang, HttpSession session) {
        if (belumLogin(session)) return "redirect:/";
        barangService.updateBarang(barang);
        return "redirect:/barang";
    }

    @GetMapping("/nonaktif/{kodeBarang}")
    public String nonaktif(@PathVariable String kodeBarang, HttpSession session, RedirectAttributes ra) {
        if (belumLogin(session)) return "redirect:/";
        try {
            barangService.nonaktifkanBarang(kodeBarang);
            ra.addFlashAttribute("sukses", "Barang berhasil dinonaktifkan.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/barang";
    }

    @GetMapping("/aktif/{kodeBarang}")
    public String aktif(@PathVariable String kodeBarang, HttpSession session, RedirectAttributes ra) {
        if (belumLogin(session)) return "redirect:/";
        try {
            barangService.aktifkanBarang(kodeBarang);
            ra.addFlashAttribute("sukses", "Barang berhasil diaktifkan kembali.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/barang";
    }

    @GetMapping("/hapus/{kodeBarang}")
    public String hapus(@PathVariable String kodeBarang, HttpSession session, RedirectAttributes ra) {
        if (belumLogin(session)) return "redirect:/";
        try {
            barangService.hapusBarang(kodeBarang);
            ra.addFlashAttribute("sukses", "Barang berhasil dihapus. Riwayat stok log tetap tersimpan.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/barang";
    }
}
