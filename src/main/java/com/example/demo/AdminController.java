package com.example.demo;

import com.example.demo.Admin;
import com.example.demo.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminRepository adminRepo;

    @GetMapping("/login")
    public String showLoginPage() {
        return "admin_login"; // HTML from earlier
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                               @RequestParam String password,
                               Model model) {
        Admin admin = adminRepo.findByEmailAndPassword(email, password);
        if (admin != null) {
            return "admin_dashboard"; // success
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "admin_login";
        }
    }
}

