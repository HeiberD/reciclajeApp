package com.proyecto.reciclaje.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reciclaje")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reciclaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String material;

    @Column(nullable = false)
    private Double cantidad;

    @Column(nullable = false)
    private String unidad;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(nullable = false)
    private String estado = "PENDIENTE"; // Estado inicial

    // 🔥 Método para asignar usuario al reciclaje
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // 🔥 Método para actualizar el estado del reciclaje
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
