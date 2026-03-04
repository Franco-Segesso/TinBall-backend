package com.futbol.TinBall_backend.controllers;

import com.futbol.TinBall_backend.models.ChatMessage;
import com.futbol.TinBall_backend.repositories.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*") // Fundamental para que React no tire errores de CORS al hacer el fetch
public class ChatHistoryController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @GetMapping("/history/{matchId}")
    public ResponseEntity<List<ChatMessage>> getChatHistory(@PathVariable Long matchId) {
        // Usamos el método mágico que creamos en el repositorio para traer los mensajes ordenados
        List<ChatMessage> history = chatMessageRepository.findByMatchIdOrderByTimestampAsc(matchId);
        
        return ResponseEntity.ok(history);
    }
}