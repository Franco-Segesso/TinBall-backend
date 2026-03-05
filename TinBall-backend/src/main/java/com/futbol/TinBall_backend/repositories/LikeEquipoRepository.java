package com.futbol.TinBall_backend.repositories;

import com.futbol.TinBall_backend.models.LikeEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LikeEquipoRepository extends JpaRepository<LikeEquipo, Long> {
    
    Optional<LikeEquipo> findFirstByEquipoEmisorIdAndEquipoReceptorId(Long emisorId, Long receptorId);

    // NUEVO: Trae directamente tus contactos guardados
    List<LikeEquipo> findByEquipoEmisorIdAndEsMatchTrue(Long emisorId);
}