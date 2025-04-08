package com.proyecto.reciclaje.service;

import com.proyecto.reciclaje.model.ConfiguracionSistema;
import com.proyecto.reciclaje.repository.ConfiguracionSistemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracionSistemaService {

    @Autowired
    private ConfiguracionSistemaRepository configuracionSistemaRepository;

    // Obtener configuración global (única fila)
    public ConfiguracionSistema obtenerConfiguracion() {
        return configuracionSistemaRepository.findTopByOrderByIdAsc();
    }

    // Actualizar configuración (solo hay una fila)
    public ConfiguracionSistema actualizarConfiguracion(ConfiguracionSistema configActualizada) {
        configActualizada.setId(obtenerConfiguracion().getId()); // mantener mismo ID
        return configuracionSistemaRepository.save(configActualizada);
    }

    // Crear configuración por primera vez si no existe
    public void inicializarConfiguracionPorDefecto() {
        if (configuracionSistemaRepository.count() == 0) {
            ConfiguracionSistema config = new ConfiguracionSistema();
            config.setPuntosPorRecoleccionExitosa(2); // valor por defecto
            configuracionSistemaRepository.save(config);
        }
    }
}
