package com.proyecto.reciclaje.controller;

import com.proyecto.reciclaje.model.Usuario;
import com.proyecto.reciclaje.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/register")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String registrarUsuario(@ModelAttribute @Valid Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Error en el formulario. Revisa los campos.");
            return "register";
        }

        if (usuarioService.existeUsuario(usuario.getUsername())) {
            model.addAttribute("error", "El nombre de usuario ya está en uso.");
            return "register";
        }

        usuario.setRole("USUARIO"); // Por defecto, los nuevos usuarios son "USUARIO"
        usuarioService.registrarUsuario(usuario);

        return "redirect:/auth/login?success";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }
}
