package com.proyecto.reciclaje.controller;

import com.proyecto.reciclaje.model.Reciclaje;
import com.proyecto.reciclaje.model.Usuario;
import com.proyecto.reciclaje.service.ReciclajeService;
import com.proyecto.reciclaje.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user/reciclaje")
public class ReciclajeController {

    @Autowired
    private ReciclajeService reciclajeService;

    @Autowired
    private UsuarioService usuarioService;

    // Mostrar formulario para registrar un nuevo reciclaje
    @GetMapping("/registrar")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("reciclaje", new Reciclaje());
        return "user/registrar_reciclaje"; // Vista del formulario
    }

    // Procesar el registro de reciclaje
    @PostMapping("/registrar")
    public String registrarReciclaje(@ModelAttribute Reciclaje reciclaje, Authentication authentication) {
        Usuario usuario = usuarioService.buscarPorUsername(authentication.getName()).orElseThrow();
        reciclaje.setUsuario(usuario);
        reciclajeService.registrarReciclaje(reciclaje);
        return "redirect:/user/dashboard?success"; // ✅ Redirige al panel del usuario, no al historial
    }

    // Mostrar historial de reciclajes del usuario
    @GetMapping("/historial")
    public String verHistorialReciclaje(Model model, Authentication authentication) {
        Usuario usuario = usuarioService.buscarPorUsername(authentication.getName()).orElseThrow();
        List<Reciclaje> historial = reciclajeService.obtenerReciclajePorUsuario(usuario);
        model.addAttribute("historial", historial);
        return "user/historial_reciclaje"; // Vista para mostrar el historial
    }
}
