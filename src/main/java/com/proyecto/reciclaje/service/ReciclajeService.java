package com.proyecto.reciclaje.service;

import com.proyecto.reciclaje.model.Reciclaje;
import com.proyecto.reciclaje.model.Usuario;
import com.proyecto.reciclaje.model.ConfiguracionSistema;
import com.proyecto.reciclaje.repository.ReciclajeRepository;
import com.proyecto.reciclaje.repository.ConfiguracionSistemaRepository;
import com.proyecto.reciclaje.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReciclajeService {

    @Autowired
    private ReciclajeRepository reciclajeRepository;

    @Autowired
    private ConfiguracionSistemaRepository configuracionSistemaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    // 🔥 Nuevo: Actualizar estado del reciclaje (RECOGIDO o RECHAZADO)
    public void actualizarEstadoReciclaje(Long id, String nuevoEstado) {
        Optional<Reciclaje> reciclajeOpt = reciclajeRepository.findById(id);
        if (reciclajeOpt.isPresent()) {
            Reciclaje reciclaje = reciclajeOpt.get();
            reciclaje.setEstado(nuevoEstado);
            reciclajeRepository.save(reciclaje);

            // ✅ Si fue aprobado (RECOGIDO), sumar puntos al usuario
            if ("RECOGIDO".equalsIgnoreCase(nuevoEstado)) {
                Usuario usuario = reciclaje.getUsuario();

                Optional<ConfiguracionSistema> configOpt = configuracionSistemaRepository.findById(1L);
                int puntos = configOpt.map(ConfiguracionSistema::getPuntosPorRecoleccionExitosa).orElse(0);

                System.out.println("Puntos actuales: " + usuario.getPuntos());
                usuario.setPuntos(usuario.getPuntos() + puntos);
                System.out.println("Puntos actualizados: " + usuario.getPuntos());

                usuarioRepository.saveAndFlush(usuario); // 👈 FORZAR escritura
            }
        }
    }
}
