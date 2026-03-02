package com.futbol.TinBall_backend.repositories;

import com.futbol.TinBall_backend.models.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    // Spring Data JPA crea la consulta automáticamente por el nombre del método
    List<Equipo> findByZonaIgnoreCase(String zona);
}