package com.PBO2.Tokoelektronikzeus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.PBO2.Tokoelektronikzeus.model.User;
import com.PBO2.Tokoelektronikzeus.service.AuthService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @GetMapping("/")
    public String loginPage() {
        return "login";
    }


        @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        User user = authService.login(username, password); 
        if (user != null) {
            session.setAttribute("loggedInUser", user.getUsername());
            return "redirect:/dashboard";
        }

        model.addAttribute("error", "Username atau password salah");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}