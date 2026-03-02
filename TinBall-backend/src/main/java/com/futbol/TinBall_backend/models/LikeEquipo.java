package com.futbol.TinBall_backend.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "likes_equipos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeEquipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipo_emisor_id", nullable = false)
    private Equipo equipoEmisor;

    @ManyToOne
    @JoinColumn(name = "equipo_receptor_id", nullable = false)
    private Equipo equipoReceptor;

    private LocalDateTime fecha = LocalDateTime.now();

    private boolean esMatch = false;
}