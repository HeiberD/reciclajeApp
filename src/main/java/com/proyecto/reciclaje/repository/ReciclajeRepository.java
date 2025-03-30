package com.proyecto.reciclaje.repository;

import com.proyecto.reciclaje.model.Reciclaje;
import com.proyecto.reciclaje.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReciclajeRepository extends JpaRepository<Reciclaje, Long> {
    List<Reciclaje> findByUsuario(Usuario usuario);

    // 🔥 Nuevo: Obtener reciclajes por estado (Ej: "PENDIENTE", "APROBADO", "RECHAZADO")
    List<Reciclaje> findByEstado(String estado);
}
