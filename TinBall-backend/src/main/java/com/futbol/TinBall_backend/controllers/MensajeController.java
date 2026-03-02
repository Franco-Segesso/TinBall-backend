package com.futbol.TinBall_backend.controllers;

import com.futbol.TinBall_backend.models.Mensaje;
import com.futbol.TinBall_backend.repositories.MensajeRepository;
import com.futbol.TinBall_backend.repositories.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensajes")
@CrossOrigin(origins = "*")
public class MensajeController {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    @PostMapping("/enviar")
    public Mensaje enviarMensaje(@RequestParam Long deId, @RequestParam Long paraId, @RequestBody String texto) {
        Mensaje m = new Mensaje();
        m.setEmisor(equipoRepository.findById(deId).get());
        m.setReceptor(equipoRepository.findById(paraId).get());
        m.setContenido(texto);
        return mensajeRepository.save(m);
    }

    @GetMapping("/historial/{id1}/{id2}")
    public List<Mensaje> obtenerChat(@PathVariable Long id1, @PathVariable Long id2) {
        return mensajeRepository.findByEmisorIdAndReceptorIdOrEmisorIdAndReceptorIdOrderByFechaEnvioAsc(id1, id2, id1, id2);
    }
}