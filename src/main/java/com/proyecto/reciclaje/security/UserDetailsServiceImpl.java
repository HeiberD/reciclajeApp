package com.proyecto.reciclaje.security;

import com.proyecto.reciclaje.model.Usuario;
import com.proyecto.reciclaje.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Intentando autenticar usuario: {}", username);

        // Obtener todos los usuarios de la base de datos y mostrarlos en consola
        List<Usuario> usuarios = usuarioRepository.findAll();
        System.out.println("Usuarios en la base de datos: " + usuarios.size());
        for (Usuario u : usuarios) {
            System.out.println("ID: " + u.getId() + ", Username: " + u.getUsername() + ", Rol: " + u.getRole());
        }

        // Buscar el usuario específico
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsernameIgnoreCase(username);
        System.out.println("Resultado de búsqueda del usuario '" + username + "': " + usuarioOptional.orElse(null));

        if (usuarioOptional.isEmpty()) {
            logger.error("Fallo de autenticación: Usuario '{}' no encontrado en la base de datos.", username);
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        Usuario usuario = usuarioOptional.get();
        logger.debug("Usuario encontrado: {} - Rol: {}", usuario.getUsername(), usuario.getRole());

        // Devuelve el usuario con contraseña encriptada
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword()) // La contraseña ya debe estar encriptada en la BD
                .roles(usuario.getRole().toUpperCase().replace("ROLE_", ""))
                .build();
    }
}
