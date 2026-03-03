package com.futbol.TinBall_backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    private String posicion; // Arquero, Defensor, Volante, Delantero
    
    private String nivel; // Amateur, Intermedio, Pro
    
    private String zona;

    private Integer edad;
    // Agrega este campo dentro de la clase Usuario.java
    private String fotoPerfilUrl; // URL de la foto del jugador
}