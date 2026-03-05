package com.futbol.TinBall_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults()) // NUEVO: Le dice a Spring Security que habilite CORS
            .csrf(csrf -> csrf.disable()) // Desactivar CSRF para pruebas
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permitimos todo
            );
        return http.build();
    }
}