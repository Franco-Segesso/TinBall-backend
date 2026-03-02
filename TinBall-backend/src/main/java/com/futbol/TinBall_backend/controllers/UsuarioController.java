package com.futbol.TinBall_backend.controllers;

import com.futbol.TinBall_backend.models.Usuario;
import com.futbol.TinBall_backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios") // Esta es la ruta base
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Prueba de vida: http://localhost:8080/api/usuarios/test
    @GetMapping("/test")
    public String test() {
        return "El backend de TinBall está funcionando correctamente";
    }

    @GetMapping
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}