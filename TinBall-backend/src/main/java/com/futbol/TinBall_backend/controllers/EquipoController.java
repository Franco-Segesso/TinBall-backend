package com.futbol.TinBall_backend.controllers;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
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


    @PostMapping("/{id}/foto")
    public Equipo subirFoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado"));

        if (file.isEmpty()) {
            throw new RuntimeException("El archivo está vacío");
        }

        try {
            // 1. Crear la carpeta "uploads" en la raíz del proyecto si no existe
            Path directorioImagenes = Paths.get("uploads");
            if (!Files.exists(directorioImagenes)) {
                Files.createDirectories(directorioImagenes);
            }

            // 2. Generar un nombre único seguro (UUID)
            String nombreArchivo = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path rutaArchivo = directorioImagenes.resolve(nombreArchivo);

            // 3. Copiar el archivo recibido a nuestra carpeta local
            Files.copy(file.getInputStream(), rutaArchivo);

            // 4. Generar la URL final y guardarla en la base de datos SQL
            String urlFoto = "http://localhost:8080/uploads/" + nombreArchivo;
            equipo.setFotoUrl(urlFoto);

            return equipoRepository.save(equipo);

        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la foto: " + e.getMessage());
        }
    }
}