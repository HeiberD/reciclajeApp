package com.proyecto.reciclaje.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        model.addAttribute("usuario", authentication.getName());
        return "user/dashboard"; // Esto apunta a templates/user/dashboard.html
    }
}
