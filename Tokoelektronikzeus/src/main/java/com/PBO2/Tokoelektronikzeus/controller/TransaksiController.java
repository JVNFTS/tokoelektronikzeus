package com.PBO2.Tokoelektronikzeus.controller;

import com.PBO2.Tokoelektronikzeus.model.*;
import com.PBO2.Tokoelektronikzeus.model.Penjualan.JenisTransaksi;
import com.PBO2.Tokoelektronikzeus.service.*;
import com.PBO2.Tokoelektronikzeus.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/transaksi")
public class TransaksiController {

    private final TransaksiService transaksiService;
    private final CustomerService customerService;
    private final BarangService barangService;
    private final UserRepository userRepository;

    public TransaksiController(TransaksiService transaksiService, CustomerService customerService, BarangService barangService, UserRepository userRepository) {
                                this.transaksiService = transaksiService;
                                this.customerService = customerService;
                                this.barangService = barangService;
                                this.userRepository = userRepository;
    }

    private boolean belumLogin(HttpSession session) {
        return session.getAttribute("loggedInUser") == null;
    }

    @GetMapping
    public String index(HttpSession session, Model model) {
        if (belumLogin(session)) return "redirect:/";
        model.addAttribute("listTransaksi", transaksiService.getAll());
        return "transaksi/index";
    }

    @GetMapping("/riwayat-restock")
    public String riwayatRestock(HttpSession session, Model model) {
        if (belumLogin(session)) return "redirect:/";
        model.addAttribute("listRestock", transaksiService.getByJenis(JenisTransaksi.RESTOCK));
        return "transaksi/riwayat-restock";
    }

    @GetMapping("/baru")
    public String formBaru(HttpSession session, Model model) {
        if (belumLogin(session)) return "redirect:/";
        model.addAttribute("noTransaksi", transaksiService.generateNoTransaksi(JenisTransaksi.JUAL));
        model.addAttribute("listBarang", barangService.getAllBarang());
        model.addAttribute("listCustomer", customerService.getAllCustomer());
        return "transaksi/form";
    }

    @PostMapping("/customer-baru")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> simpanCustomerBaru(
            @RequestParam String namaCustomer,
            @RequestParam(required = false) String noTelepon,
            @RequestParam(required = false) String alamat,
            HttpSession session) {

        if (belumLogin(session)) return ResponseEntity.status(401).build();

        try {
            Customer c = transaksiService.simpanCustomerBaru(namaCustomer, noTelepon, alamat);
            Map<String, Object> result = new HashMap<>();
            result.put("id", c.getId());
            result.put("namaCustomer", c.getNamaCustomer());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/simpan")
    public String simpan(
            @RequestParam String noTransaksi,
            @RequestParam(required = false) Long customerId,
            @RequestParam String keterangan,
            @RequestParam List<String> kodeBarang,
            @RequestParam List<Integer> jumlah,
            HttpSession session,
            RedirectAttributes ra) {

        if (belumLogin(session)) return "redirect:/";

        try {
            String usernameLogin = (String) session.getAttribute("loggedInUser");
            User user = userRepository.findByUsername(usernameLogin)
                    .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

            Penjualan penjualan = new Penjualan();
            penjualan.setNoTransaksi(noTransaksi);
            penjualan.setJenisTransaksi(JenisTransaksi.JUAL);
            penjualan.setUser(user);
            penjualan.setKeterangan(keterangan);

            if (customerId != null) {
                penjualan.setCustomer(customerService.getById(customerId));
            }

            List<PenjualanDetail> details = new ArrayList<>();
            for (int i = 0; i < kodeBarang.size(); i++) {
                Barang barang = new Barang();
                barang.setKodeBarang(kodeBarang.get(i));
                PenjualanDetail detail = new PenjualanDetail();
                detail.setBarang(barang);
                detail.setJumlah(jumlah.get(i));
                details.add(detail);
            }

            transaksiService.simpanTransaksi(penjualan, details);
            ra.addFlashAttribute("sukses", "Transaksi " + noTransaksi + " berhasil disimpan.");

        } catch (Exception e) {
            ra.addFlashAttribute("error", "Gagal: " + e.getMessage());
            return "redirect:/transaksi/baru";
        }

        return "redirect:/transaksi";
    }

    @PostMapping("/hapus/{noTransaksi}")
    public String hapus(@PathVariable String noTransaksi,
                        HttpSession session,
                        RedirectAttributes ra) {

        if (belumLogin(session)) return "redirect:/";

        JenisTransaksi jenis = null;
        try {
            Penjualan p = transaksiService.getByNo(noTransaksi);
            if (p == null) throw new RuntimeException("Transaksi tidak ditemukan");

            jenis = p.getJenisTransaksi();
            transaksiService.hapusTransaksi(noTransaksi);
            ra.addFlashAttribute("sukses", "Transaksi " + noTransaksi + " berhasil dihapus dan stok telah disesuaikan.");

        } catch (Exception e) {
            ra.addFlashAttribute("error", "Gagal hapus: " + e.getMessage());
        }

        if (jenis == JenisTransaksi.RESTOCK) {
            return "redirect:/transaksi/riwayat-restock";
        }
        return "redirect:/transaksi";
    }

    @GetMapping("/restock")
    public String formRestock(HttpSession session, Model model) {
        if (belumLogin(session)) return "redirect:/";
        model.addAttribute("noTransaksi", transaksiService.generateNoTransaksi(JenisTransaksi.RESTOCK));
        model.addAttribute("listBarang", barangService.getAllBarang());
        return "transaksi/restock";
    }

    @PostMapping("/restock/simpan")
    public String simpanRestock(
            @RequestParam String noTransaksi,
            @RequestParam String keterangan,
            @RequestParam List<String> kodeBarang,
            @RequestParam List<Integer> jumlah,
            HttpSession session,
            RedirectAttributes ra) {

        if (belumLogin(session)) return "redirect:/";

        try {
            String usernameLogin = (String) session.getAttribute("loggedInUser");
            User user = userRepository.findByUsername(usernameLogin)
                    .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

            Penjualan penjualan = new Penjualan();
            penjualan.setNoTransaksi(noTransaksi);
            penjualan.setUser(user);
            penjualan.setKeterangan(keterangan);

            List<PenjualanDetail> details = new ArrayList<>();
            for (int i = 0; i < kodeBarang.size(); i++) {
                String kode = kodeBarang.get(i).trim();
                if (kode.isEmpty()) continue;
                Barang barang = new Barang();
                barang.setKodeBarang(kode);
                PenjualanDetail detail = new PenjualanDetail();
                detail.setBarang(barang);
                detail.setJumlah(jumlah.get(i));
                details.add(detail);
            }

            transaksiService.simpanRestock(penjualan, details);
            ra.addFlashAttribute("sukses", "Restock " + noTransaksi + " berhasil disimpan.");

        } catch (Exception e) {
            ra.addFlashAttribute("error", "Gagal: " + e.getMessage());
            return "redirect:/transaksi/restock";
        }

        return "redirect:/transaksi/riwayat-restock";
    }

    @GetMapping("/detail/{noTransaksi}")
    public String detail(@PathVariable String noTransaksi,
                         HttpSession session, Model model) {
        if (belumLogin(session)) return "redirect:/";
        model.addAttribute("penjualan", transaksiService.getByNo(noTransaksi));
        model.addAttribute("details", transaksiService.getDetailByNo(noTransaksi));
        return "transaksi/detail";
    }
}
