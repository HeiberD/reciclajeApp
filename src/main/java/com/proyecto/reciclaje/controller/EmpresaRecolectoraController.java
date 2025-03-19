package com.proyecto.reciclaje.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empresa-recolectora") // 🔥 Cambia la ruta del endpoint
public class EmpresaRecolectoraController {

    @GetMapping("/dashboard")
    public String empresaRecolectoraDashboard(Model model, Authentication authentication) {
        model.addAttribute("usuario", authentication.getName());
        return "empresa-recolectora/dashboard"; // 🔥 Asegúrate de que la vista exista en templates
    }
}
