package com.PBO2.Tokoelektronikzeus.controller;

import com.PBO2.Tokoelektronikzeus.model.Customer;
import com.PBO2.Tokoelektronikzeus.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    private boolean belumLogin(HttpSession session) {
        return session.getAttribute("loggedInUser") == null;
    }

    @GetMapping
    public String index(HttpSession session, Model model,
                        @RequestParam(required = false) String keyword) {
        if (belumLogin(session)) return "redirect:/";
        model.addAttribute("listCustomer",
                keyword != null && !keyword.isBlank()
                        ? customerService.cari(keyword)
                        : customerService.getAllCustomer());
        model.addAttribute("keyword", keyword);
        return "customer/index";
    }

    @GetMapping("/tambah")
    public String tambahForm(HttpSession session, Model model) {
        if (belumLogin(session)) return "redirect:/";
        model.addAttribute("customer", new Customer());
        model.addAttribute("mode", "tambah");
        return "customer/form";
    }

    @PostMapping("/simpan")
    public String simpan(@ModelAttribute Customer customer, HttpSession session, RedirectAttributes ra) {
        if (belumLogin(session)) return "redirect:/";
        try {
            customerService.tambah(customer);
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/customer/tambah";
        }
        return "redirect:/customer";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, HttpSession session, Model model) {
        if (belumLogin(session)) return "redirect:/";
        model.addAttribute("customer", customerService.getById(id));
        model.addAttribute("mode", "edit");
        return "customer/form";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Customer customer, HttpSession session, RedirectAttributes ra) {
        if (belumLogin(session)) return "redirect:/";
        try {
            customerService.update(customer);
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/customer/edit/" + customer.getId();
        }
        return "redirect:/customer";
    }

    @GetMapping("/hapus/{id}")
    public String hapus(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        if (belumLogin(session)) return "redirect:/";
        try {
            customerService.hapus(id);
            ra.addFlashAttribute("sukses", "Customer berhasil dihapus.");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/customer";
    }
}
