package com.futbol.TinBall_backend.controllers;

import com.futbol.TinBall_backend.exceptions.ResourceNotFoundException;
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
    // Modifica el método de agregar jugador para usar la nueva excepción
    @PostMapping("/{equipoId}/usuarios/{usuarioId}")
    public Equipo agregarJugadorAEquipo(@PathVariable Long equipoId, @PathVariable Long usuarioId) {
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el equipo con ID: " + equipoId));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario con ID: " + usuarioId));
        
        if (!equipo.getJugadores().contains(usuario)) {
            equipo.getJugadores().add(usuario);
        }
        return equipoRepository.save(equipo);
    }
}