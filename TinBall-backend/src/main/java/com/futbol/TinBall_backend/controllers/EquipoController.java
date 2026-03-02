package com.futbol.TinBall_backend.controllers;

import com.futbol.TinBall_backend.models.Equipo;
import com.futbol.TinBall_backend.models.Usuario;
import com.futbol.TinBall_backend.repositories.EquipoRepository;
import com.futbol.TinBall_backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
@CrossOrigin(origins = "*")
public class EquipoController {

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Equipo> obtenerTodos() {
        return equipoRepository.findAll();
    }

    @PostMapping
    public Equipo crearEquipo(@RequestBody Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    // Agregar este método dentro de EquipoController.java
    @GetMapping("/filtrar")
    public List<Equipo> filtrarPorZona(@RequestParam String zona) {
    return equipoRepository.findByZonaIgnoreCase(zona);
    }

    // NUEVO: Endpoint para agregar un jugador a un equipo
    @PostMapping("/{equipoId}/usuarios/{usuarioId}")
    public Equipo agregarJugadorAEquipo(@PathVariable Long equipoId, @PathVariable Long usuarioId) {
        // Buscamos el equipo y el usuario por sus IDs
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Agregamos el usuario a la lista del equipo
        equipo.getJugadores().add(usuario);
        
        // Guardamos los cambios
        return equipoRepository.save(equipo);
    }
}