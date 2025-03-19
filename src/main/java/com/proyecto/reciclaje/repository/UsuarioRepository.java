package com.proyecto.reciclaje.repository;

import com.proyecto.reciclaje.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

    // Método para buscar el usuario sin importar mayúsculas/minúsculas
    Optional<Usuario> findByUsernameIgnoreCase(String username);
}
