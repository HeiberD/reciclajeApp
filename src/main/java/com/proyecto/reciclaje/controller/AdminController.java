package com.proyecto.reciclaje.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String adminDashboard(Model model, Authentication authentication) {
        model.addAttribute("usuario", authentication.getName());
        return "admin/dashboard"; // Vista para administradores
    }
}
