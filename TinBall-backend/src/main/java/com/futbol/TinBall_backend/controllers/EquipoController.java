package com.futbol.TinBall_backend.controllers;

import com.futbol.TinBall_backend.models.Equipo;
import com.futbol.TinBall_backend.models.Usuario;
import com.futbol.TinBall_backend.repositories.EquipoRepository;
import com.futbol.TinBall_backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/equipos")
@CrossOrigin(origins = "*")
public class EquipoController {

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository; // Importamos el repositorio de jugadores

    @GetMapping
    public List<Equipo> obtenerEquipos() {
        return equipoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerEquipoPorId(@PathVariable Long id) {
        Optional<Equipo> equipo = equipoRepository.findById(id);
        if(equipo.isPresent()){
            return ResponseEntity.ok(equipo.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipo no encontrado");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEquipo(@PathVariable Long id, @RequestBody Equipo equipoActualizado) {
        Optional<Equipo> equipoOpt = equipoRepository.findById(id);
        
        if (equipoOpt.isPresent()) {
            Equipo equipo = equipoOpt.get();
            equipo.setNombre(equipoActualizado.getNombre());
            equipo.setZona(equipoActualizado.getZona());
            equipo.setNivelPromedio(equipoActualizado.getNivelPromedio());
            equipo.setDescripcion(equipoActualizado.getDescripcion());
            
            equipoRepository.save(equipo);
            return ResponseEntity.ok(equipo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipo no encontrado");
        }
    }

    // NUEVO: Método para agregar un jugador al equipo mediante su email
    @PostMapping("/{id}/jugadores")
    public ResponseEntity<?> agregarJugadorPorEmail(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        
        Optional<Equipo> equipoOpt = equipoRepository.findById(id);
        if (equipoOpt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipo no encontrado");
        
        Optional<Usuario> jugadorOpt = usuarioRepository.findByEmail(email);
        if (jugadorOpt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe ningún jugador con ese email registrado.");

        Equipo equipo = equipoOpt.get();
        Usuario jugador = jugadorOpt.get();

        // Si la lista está vacía la inicializamos para evitar errores
        if (equipo.getJugadores() == null) {
            equipo.setJugadores(new HashSet<>());
        }

        // Evitamos agregar al mismo jugador dos veces
        if (equipo.getJugadores().contains(jugador)) {
            return ResponseEntity.badRequest().body("Este jugador ya forma parte de tu equipo.");
        }

        equipo.getJugadores().add(jugador);
        equipoRepository.save(equipo);

        return ResponseEntity.ok(equipo);
    }

    @PostMapping("/{id}/foto")
    public ResponseEntity<?> subirFoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            Equipo equipo = equipoRepository.findById(id).orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
            if (file.isEmpty()) return ResponseEntity.badRequest().body("Archivo vacío");

            String rutaProyecto = System.getProperty("user.dir");
            Path directorioImagenes = Paths.get(rutaProyecto, "uploads");
            if (!Files.exists(directorioImagenes)) Files.createDirectories(directorioImagenes);

            String nombreArchivo = UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replace(" ", "_");
            Path rutaArchivo = directorioImagenes.resolve(nombreArchivo);
            Files.copy(file.getInputStream(), rutaArchivo);

            String urlFoto = "https://tinball-backend-1.onrender.com/uploads/" + nombreArchivo;
            equipo.setFotoUrl(urlFoto);
            equipoRepository.save(equipo);

            return ResponseEntity.ok(equipo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir imagen: " + e.getMessage());
        }
    }
}