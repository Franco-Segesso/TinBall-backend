package com.futbol.TinBall_backend.repositories;

import com.futbol.TinBall_backend.models.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    Optional<Equipo> findByEmailAndPassword(String email, String password);
    
    // NUEVO: Buscar todos los equipos en los que juega un usuario específico
    List<Equipo> findByJugadoresId(Long jugadorId);
}