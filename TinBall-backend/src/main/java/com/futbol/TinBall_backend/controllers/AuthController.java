package com.futbol.TinBall_backend.controllers;

import com.futbol.TinBall_backend.models.Usuario;
import com.futbol.TinBall_backend.models.Equipo;
import com.futbol.TinBall_backend.repositories.UsuarioRepository;
import com.futbol.TinBall_backend.repositories.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    @PostMapping("/registro/equipo")
    public ResponseEntity<?> registroEquipo(@RequestBody Equipo nuevoEquipo) {
        try {
            return ResponseEntity.ok(equipoRepository.saveAndFlush(nuevoEquipo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ERROR DE EQUIPO: " + e.getMessage());
        }
    }

    @PostMapping("/login/equipo")
    public ResponseEntity<?> loginEquipo(@RequestBody Equipo loginData) {
        Optional<Equipo> equipo = equipoRepository.findByEmailAndPassword(loginData.getEmail(), loginData.getPassword());
        if (equipo.isPresent()) {
            return ResponseEntity.ok(equipo.get());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email o contraseña de equipo incorrectos");
    }

    @PostMapping("/registro/jugador")
    public ResponseEntity<?> registroJugador(@RequestBody Usuario nuevoJugador) {
        try {
            System.out.println(">>> INTENTANDO GUARDAR JUGADOR: " + nuevoJugador.getEmail());
            // saveAndFlush fuerza a la base de datos a responder YA MISMO
            Usuario guardado = usuarioRepository.saveAndFlush(nuevoJugador);
            System.out.println(">>> JUGADOR GUARDADO CON ÉXITO");
            return ResponseEntity.ok(guardado);
        } catch (Exception e) {
            System.out.println(">>> EXPLOSIÓN AL GUARDAR:");
            e.printStackTrace();
            String causa = (e.getCause() != null) ? e.getCause().getMessage() : "Desconocida";
            // Escupimos el error técnico crudo hacia el frontend para que lo leas
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ERROR FATAL BD: " + e.getMessage() + " | CAUSA: " + causa);
        }
    }

    @PostMapping("/login/jugador")
    public ResponseEntity<?> loginJugador(@RequestBody Usuario loginData) {
        Optional<Usuario> jugador = usuarioRepository.findByEmailAndPassword(loginData.getEmail(), loginData.getPassword());
        if (jugador.isPresent()) {
            return ResponseEntity.ok(jugador.get());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email o contraseña de jugador incorrectos");
    }
}