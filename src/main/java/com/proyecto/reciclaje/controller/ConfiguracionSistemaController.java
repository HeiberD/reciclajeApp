package com.proyecto.reciclaje.controller;

import com.proyecto.reciclaje.model.ConfiguracionSistema;
import com.proyecto.reciclaje.service.ConfiguracionSistemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/configuracion")
public class ConfiguracionSistemaController {

    @Autowired
    private ConfiguracionSistemaService configuracionSistemaService;

    @GetMapping
    public String verConfiguracion(Model model) {
        ConfiguracionSistema config = configuracionSistemaService.obtenerConfiguracion();
        model.addAttribute("configuracion", config);
        return "admin/configuracion_sistema"; // Asegúrate de tener esta plantilla HTML
    }

    @PostMapping("/actualizar")
    public String actualizarConfiguracion(@ModelAttribute ConfiguracionSistema configuracion) {
        configuracionSistemaService.actualizarConfiguracion(configuracion);
        return "redirect:/admin/configuracion?success";
    }
}
