package com.proyecto.reciclaje.controller;

import com.proyecto.reciclaje.model.Reciclaje;
import com.proyecto.reciclaje.service.ReciclajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/empresa-recolectora")
public class EmpresaRecolectoraController {

    @Autowired
    private ReciclajeService reciclajeService;

    @GetMapping("/dashboard")
    public String empresaRecolectoraDashboard(Model model, Authentication authentication) {
        model.addAttribute("usuario", authentication.getName());
        return "empresa-recolectora/dashboard";
    }

    // 🔹 Mostrar reciclajes pendientes
    @GetMapping("/reciclajes-pendientes")
    public String verReciclajesPendientes(Model model) {
        List<Reciclaje> reciclajesPendientes = reciclajeService.obtenerReciclajesPendientes();
        model.addAttribute("reciclajes", reciclajesPendientes);
        return "empresa-recolectora/reciclajes-pendientes"; // Asegurar que la vista exista
    }

    // 🔹 Aprobar reciclaje
    @PostMapping("/aprobar/{id}")
    public String aprobarReciclaje(@PathVariable Long id) {
        reciclajeService.actualizarEstadoReciclaje(id, "RECOGIDO");
        return "redirect:/empresa-recolectora/reciclajes-pendientes";
    }

    // 🔹 Rechazar reciclaje
    @PostMapping("/rechazar/{id}")
    public String rechazarReciclaje(@PathVariable Long id) {
        reciclajeService.actualizarEstadoReciclaje(id, "RECHAZADO");
        return "redirect:/empresa-recolectora/reciclajes-pendientes";
    }
}
