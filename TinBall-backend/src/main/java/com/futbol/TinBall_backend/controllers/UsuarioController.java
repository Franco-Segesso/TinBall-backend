package com.futbol.TinBall_backend.controllers;

import com.futbol.TinBall_backend.models.Usuario;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.futbol.TinBall_backend.models.Equipo;
import com.futbol.TinBall_backend.repositories.UsuarioRepository;
import com.futbol.TinBall_backend.repositories.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EquipoRepository equipoRepository; // Importamos el repositorio de equipos

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuario(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isPresent()){
            return ResponseEntity.ok(usuario.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jugador no encontrado");
    }

    // NUEVO: Ruta para devolver los equipos en los que juega este usuario
    @GetMapping("/{id}/equipos")
    public ResponseEntity<?> getEquiposDeJugador(@PathVariable Long id) {
        try {
            List<Equipo> equipos = equipoRepository.findByJugadoresId(id);
            return ResponseEntity.ok(equipos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar equipos: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setZona(usuarioActualizado.getZona());
            usuario.setPosicion(usuarioActualizado.getPosicion());
            usuario.setEdad(usuarioActualizado.getEdad());
            usuario.setNivel(usuarioActualizado.getNivel());
            
            usuarioRepository.save(usuario);
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jugador no encontrado");
        }
    }

    @Autowired
    private Cloudinary cloudinary;

    @PostMapping("/{id}/foto")
    public ResponseEntity<?> subirFoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
    try {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        
        // Subimos a Cloudinary directamente
        var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String urlFoto = (String) uploadResult.get("url");

        usuario.setFotoPerfilUrl(urlFoto);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(usuario);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error al subir a Cloudinary: " + e.getMessage());
    }
}
}