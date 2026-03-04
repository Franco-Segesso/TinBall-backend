package com.futbol.TinBall_backend.controllers;

import com.futbol.TinBall_backend.models.LikeEquipo;
import com.futbol.TinBall_backend.models.Equipo;
import com.futbol.TinBall_backend.repositories.LikeEquipoRepository;
import com.futbol.TinBall_backend.repositories.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/likes")
@CrossOrigin(origins = "*")
public class LikeEquipoController {

    @Autowired
    private LikeEquipoRepository likeRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    @PostMapping("/dar-like/{emisorId}/{receptorId}")
    public ResponseEntity<?> darLike(@PathVariable Long emisorId, @PathVariable Long receptorId) {
        
        // 0. Seguridad: Un equipo no puede darse like a sí mismo
        if (emisorId.equals(receptorId)) {
            return ResponseEntity.badRequest().body("No puedes darte like a ti mismo.");
        }

        // 1. Verificar que los equipos existan usando Optional seguro
        Optional<Equipo> emisorOpt = equipoRepository.findById(emisorId);
        Optional<Equipo> receptorOpt = equipoRepository.findById(receptorId);

        if (emisorOpt.isEmpty() || receptorOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Uno de los equipos no existe.");
        }

        // 2. Seguridad: Evitar Likes duplicados en la base de datos
        Optional<LikeEquipo> likeExistente = likeRepository.findFirstByEquipoEmisorIdAndEquipoReceptorId(emisorId, receptorId);
        if (likeExistente.isPresent()) {
            return ResponseEntity.ok("Ya le habías dado like a este equipo.");
        }

        Equipo emisor = emisorOpt.get();
        Equipo receptor = receptorOpt.get();

        LikeEquipo nuevoLike = new LikeEquipo();
        nuevoLike.setEquipoEmisor(emisor);
        nuevoLike.setEquipoReceptor(receptor);
        
        // 3. Verificar si el otro equipo ya nos había dado like (¡MATCH!)
        Optional<LikeEquipo> likeInverso = likeRepository.findFirstByEquipoEmisorIdAndEquipoReceptorId(receptorId, emisorId);
        
        if (likeInverso.isPresent()) {
            nuevoLike.setEsMatch(true);
            LikeEquipo inverso = likeInverso.get();
            inverso.setEsMatch(true);
            
            // Guardamos ambos como match
            likeRepository.save(inverso);
            likeRepository.save(nuevoLike);
            
            return ResponseEntity.ok("¡IT'S A MATCH! Ambos equipos quieren jugar.");
        }
        
        // Si no hay match todavía, solo guardamos nuestro like
        likeRepository.save(nuevoLike);
        return ResponseEntity.ok("Like enviado correctamente.");
    }
}