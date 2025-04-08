package com.proyecto.reciclaje.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private com.proyecto.reciclaje.service.UsuarioService usuarioService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        com.proyecto.reciclaje.model.Usuario usuario = usuarioService
                .buscarPorUsername(authentication.getName())
                .orElseThrow();
        model.addAttribute("usuario", usuario);
        return "user/dashboard";
    }

}
