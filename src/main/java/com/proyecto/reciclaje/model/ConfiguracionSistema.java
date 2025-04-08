package com.proyecto.reciclaje.model;

import jakarta.persistence.*;

@Entity
@Table(name = "configuracion_sistema")
public class ConfiguracionSistema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int puntosPorRecoleccionExitosa;

    public ConfiguracionSistema() {}

    public ConfiguracionSistema(Long id, int puntosPorRecoleccionExitosa) {
        this.id = id;
        this.puntosPorRecoleccionExitosa = puntosPorRecoleccionExitosa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPuntosPorRecoleccionExitosa() {
        return puntosPorRecoleccionExitosa;
    }

    public void setPuntosPorRecoleccionExitosa(int puntosPorRecoleccionExitosa) {
        this.puntosPorRecoleccionExitosa = puntosPorRecoleccionExitosa;
    }
}

