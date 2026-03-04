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

    // --- CUENTAS DE TIPO EQUIPO ---

    @PostMapping("/registro/equipo")
    public ResponseEntity<?> registroEquipo(@RequestBody Equipo nuevoEquipo) {
        try {
            return ResponseEntity.ok(equipoRepository.save(nuevoEquipo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al registrar equipo. ¿Email repetido?");
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

    // --- CUENTAS DE TIPO JUGADOR (Usuario.java) ---

    @PostMapping("/registro/jugador")
    public ResponseEntity<?> registroJugador(@RequestBody Usuario nuevoJugador) {
        try {
            return ResponseEntity.ok(usuarioRepository.save(nuevoJugador));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al registrar jugador.");
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