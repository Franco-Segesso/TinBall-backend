package com.futbol.TinBall_backend.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "equipos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String zona;

    private String nivelPromedio;

    private String descripcion;

    @ManyToMany
    @JoinTable(
      name = "equipo_jugadores", 
      joinColumns = @JoinColumn(name = "equipo_id"), 
      inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> jugadores;
}