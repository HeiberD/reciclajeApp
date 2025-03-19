package com.proyecto.reciclaje.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "home"; // Asegúrate de tener un archivo "home.html" en templates
    }
}
