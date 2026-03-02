package com.futbol.TinBall_backend.controllers;

import com.futbol.TinBall_backend.models.LikeEquipo;
import com.futbol.TinBall_backend.models.Equipo;
import com.futbol.TinBall_backend.repositories.LikeEquipoRepository;
import com.futbol.TinBall_backend.repositories.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String darLike(@PathVariable Long emisorId, @PathVariable Long receptorId) {
        
        // 1. Guardar el Like actual
        Equipo emisor = equipoRepository.findById(emisorId).get();
        Equipo receptor = equipoRepository.findById(receptorId).get();
        
        LikeEquipo nuevoLike = new LikeEquipo();
        nuevoLike.setEquipoEmisor(emisor);
        nuevoLike.setEquipoReceptor(receptor);
        
        // 2. Verificar si el otro equipo ya nos había dado like
        Optional<LikeEquipo> likeInverso = likeRepository.findByEquipoEmisorIdAndEquipoReceptorId(receptorId, emisorId);
        
        if (likeInverso.isPresent()) {
            nuevoLike.setEsMatch(true);
            likeInverso.get().setEsMatch(true);
            likeRepository.save(likeInverso.get());
            likeRepository.save(nuevoLike);
            return "¡IT'S A MATCH! Ambos equipos quieren jugar.";
        }
        
        likeRepository.save(nuevoLike);
        return "Like enviado correctamente.";
    }
}