package com.proyecto.reciclaje.repository;

import com.proyecto.reciclaje.model.ConfiguracionSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracionSistemaRepository extends JpaRepository<ConfiguracionSistema, Long> {

    // Como solo habrá una configuración, este método devuelve la primera fila
    ConfiguracionSistema findTopByOrderByIdAsc();
}
