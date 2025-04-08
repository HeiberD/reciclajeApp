package com.proyecto.reciclaje.service;

import com.proyecto.reciclaje.model.Usuario;
import com.proyecto.reciclaje.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // Encripta la contraseña
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    // Nuevo método para verificar si el usuario ya existe en la base de datos
    public boolean existeUsuario(String username) {
        return usuarioRepository.findByUsername(username).isPresent();

    }
    // 🔥 Nuevo método para actualizar usuario
    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);

        }

    }

