package com.proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.proyecto.reciclaje")

public class ReciclajeAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReciclajeAppApplication.class, args);
	}

}
