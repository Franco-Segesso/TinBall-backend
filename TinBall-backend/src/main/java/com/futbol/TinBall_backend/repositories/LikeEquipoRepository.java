package com.futbol.TinBall_backend.repositories;

import com.futbol.TinBall_backend.models.LikeEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LikeEquipoRepository extends JpaRepository<LikeEquipo, Long> {
    // findFirstBy evita que Java crashee si en las pruebas guardaste el mismo like 2 veces
    Optional<LikeEquipo> findFirstByEquipoEmisorIdAndEquipoReceptorId(Long emisorId, Long receptorId);
}