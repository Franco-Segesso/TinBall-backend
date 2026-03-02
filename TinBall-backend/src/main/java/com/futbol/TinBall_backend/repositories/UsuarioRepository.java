package com.futbol.TinBall_backend.repositories;

import com.futbol.TinBall_backend.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Aquí JpaRepository ya nos da métodos como save(), findAll(), findById(), etc.
}