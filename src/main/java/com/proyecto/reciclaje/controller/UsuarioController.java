package com.proyecto.reciclaje.controller;

import com.proyecto.reciclaje.model.Usuario;
import com.proyecto.reciclaje.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    // 🔥 NUEVO: Mostrar perfil del usuario (solo lectura)
    @GetMapping("/perfil")
    public String verPerfil(Model model, Authentication authentication) {
        Usuario usuario = usuarioService.buscarPorUsername(authentication.getName()).orElseThrow();
        model.addAttribute("usuario", usuario);
        return "user/editar_perfil";
    }

    // 🔥 NUEVO: Procesar actualización de datos
    @PostMapping("/editar")
    public String actualizarDatos(@ModelAttribute Usuario usuario, Authentication authentication) {
        Usuario actual = usuarioService.buscarPorUsername(authentication.getName()).orElseThrow();
        actual.setUsername(usuario.getUsername()); // ⚠️ Solo si lo permites cambiar usuario
        usuarioService.actualizarUsuario(actual);
        return "redirect:/user/dashboard";
    }

    // 🔥 NUEVO: Mostrar formulario para cambiar contraseña
    @GetMapping("/cambiar-contrasena")
    public String mostrarFormularioCambioContrasena() {
        return "user/cambiar_contrasena";
    }

    // 🔥 NUEVO: Procesar cambio de contraseña
    @PostMapping("/cambiar-contrasena")
    public String cambiarContrasena(@RequestParam String nuevaContrasena,
                                    Authentication authentication) {
        Usuario usuario = usuarioService.buscarPorUsername(authentication.getName()).orElseThrow();
        usuario.setPassword(passwordEncoder.encode(nuevaContrasena));
        usuarioService.actualizarUsuario(usuario);
        return "redirect:/user/dashboard";
    }
}
