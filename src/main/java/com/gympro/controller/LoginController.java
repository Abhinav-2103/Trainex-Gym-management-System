package com.gympro.controller;

import com.gympro.entity.Admin;
import com.gympro.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("admin", new Admin());
        return "register";
    }

    @PostMapping("/register")
    public String registerAdmin(@RequestParam String username,
                                @RequestParam(required = false) String email,
                                @RequestParam String password,
                                @RequestParam String confirmPassword,
                                RedirectAttributes redirectAttributes) {

        // Validate passwords match
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match!");
            return "redirect:/register";
        }

        // Check unique username
        if (adminRepository.existsByUsername(username)) {
            redirectAttributes.addFlashAttribute("error", "Username already exists!");
            return "redirect:/register";
        }

        // Check unique email if provided
        if (email != null && !email.isBlank() && adminRepository.existsByEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "Email already registered!");
            return "redirect:/register";
        }

        // Create and save admin
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(password));
        adminRepository.save(admin);

        redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
        return "redirect:/login";
    }
}
