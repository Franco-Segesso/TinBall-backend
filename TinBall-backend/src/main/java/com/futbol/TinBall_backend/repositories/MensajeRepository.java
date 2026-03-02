package com.futbol.TinBall_backend.repositories;

import com.futbol.TinBall_backend.models.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    // Obtener el historial entre dos equipos
    List<Mensaje> findByEmisorIdAndReceptorIdOrEmisorIdAndReceptorIdOrderByFechaEnvioAsc(
        Long id1, Long id2, Long id2Again, Long id1Again
    );
}