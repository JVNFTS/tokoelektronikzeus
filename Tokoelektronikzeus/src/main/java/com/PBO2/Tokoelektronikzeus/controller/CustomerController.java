package com.PBO2.Tokoelektronikzeus.controller;

import com.PBO2.Tokoelektronikzeus.model.Customer;
import com.PBO2.Tokoelektronikzeus.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String simpan(@ModelAttribute Customer customer, HttpSession session) {
        if (belumLogin(session)) return "redirect:/";
        customerService.simpan(customer);
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
    public String update(@ModelAttribute Customer customer, HttpSession session) {
        if (belumLogin(session)) return "redirect:/";
        customerService.simpan(customer);
        return "redirect:/customer";
    }

    @GetMapping("/hapus/{id}")
    public String hapus(@PathVariable Long id, HttpSession session) {
        if (belumLogin(session)) return "redirect:/";
        customerService.hapus(id);
        return "redirect:/customer";
    }
}
