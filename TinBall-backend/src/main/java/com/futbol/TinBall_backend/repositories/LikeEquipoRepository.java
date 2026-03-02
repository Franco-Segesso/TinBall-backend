package com.futbol.TinBall_backend.repositories;

import com.futbol.TinBall_backend.models.LikeEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LikeEquipoRepository extends JpaRepository<LikeEquipo, Long> {
    // Busca si ya existe un like de vuelta para confirmar el match
    Optional<LikeEquipo> findByEquipoEmisorIdAndEquipoReceptorId(Long emisorId, Long receptorId);
}