package com.proyecto.reciclaje.service;

import com.proyecto.reciclaje.model.Reciclaje;
import com.proyecto.reciclaje.model.Usuario;
import com.proyecto.reciclaje.repository.ReciclajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReciclajeService {

    @Autowired
    private ReciclajeRepository reciclajeRepository;

    public Reciclaje registrarReciclaje(Reciclaje reciclaje) {
        return reciclajeRepository.save(reciclaje);
    }

    public List<Reciclaje> obtenerReciclajePorUsuario(Usuario usuario) {
        return reciclajeRepository.findByUsuario(usuario);
    }

    // 🔥 Nuevo: Obtener reciclajes con estado "PENDIENTE"
    public List<Reciclaje> obtenerReciclajesPendientes() {
        return reciclajeRepository.findByEstado("PENDIENTE");
    }

    // 🔥 Nuevo: Actualizar estado del reciclaje (APROBADO o RECHAZADO)
    public void actualizarEstadoReciclaje(Long id, String nuevoEstado) {
        Optional<Reciclaje> reciclajeOpt = reciclajeRepository.findById(id);
        if (reciclajeOpt.isPresent()) {
            Reciclaje reciclaje = reciclajeOpt.get();
            reciclaje.setEstado(nuevoEstado);
            reciclajeRepository.save(reciclaje);
        }
    }
}
