package com.proyecto.reciclaje.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.debug("Instanciando BCryptPasswordEncoder para el cifrado de contraseñas.");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        logger.debug("Instanciando AuthenticationManager.");
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.debug("Configurando reglas de seguridad en SecurityFilterChain.");

        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/logout", "/user/reciclaje/registrar", "/empresa-recolectora/aprobar/**") // 🔥 Deshabilita CSRF en estas rutas
                )
                .authorizeHttpRequests(auth -> {
                    logger.debug("Configurando autorizaciones de rutas.");
                    auth
                            .requestMatchers("/auth/register", "/login", "/css/**", "/js/**", "/img/**").permitAll()
                            .requestMatchers("/admin/**").hasRole("ADMIN") // Solo ADMIN accede a /admin/**
                            .requestMatchers("/user/**").hasRole("USUARIO") // Solo USUARIO accede a /user/**
                            .requestMatchers("/empresa-recolectora/**").hasRole("EMPRESA_RECOLECTORA")
                            .requestMatchers("/").permitAll()
                            .anyRequest().authenticated();
                })
                .formLogin(login -> {
                    logger.debug("Configurando página de inicio de sesión.");
                    login
                            .loginPage("/login")
                            .successHandler((request, response, authentication) -> {
                                String redirectUrl = "/home";
                                if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                                    redirectUrl = "/admin/dashboard";
                                } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USUARIO"))) {
                                    redirectUrl = "/user/dashboard";
                                } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPRESA_RECOLECTORA"))) {
                                    redirectUrl = "/empresa-recolectora/dashboard";
                                }
                                response.sendRedirect(redirectUrl);
                            })
                            .permitAll();
                })
                .logout(logout -> {
                    logger.debug("Configurando logout.");
                    logout
                            .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST")) // 🔥 Asegura que el logout sea con POST
                            .logoutSuccessUrl("/login?logout") // 🔥 Redirige a login con mensaje de éxito
                            .invalidateHttpSession(true) // 🔥 Invalida la sesión actual
                            .deleteCookies("JSESSIONID") // 🔥 Borra las cookies de sesión
                            .permitAll();
                });

        logger.debug("Finalizando configuración de SecurityFilterChain.");
        return http.build();
    }
}

