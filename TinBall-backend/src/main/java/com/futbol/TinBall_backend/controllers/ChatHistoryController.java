package com.futbol.TinBall_backend.controllers;

import com.futbol.TinBall_backend.models.ChatMessage;
import com.futbol.TinBall_backend.repositories.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatHistoryController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @GetMapping("/history/{salaId}")
    public ResponseEntity<List<ChatMessage>> getChatHistory(@PathVariable String salaId) {
        List<ChatMessage> history = chatMessageRepository.findBySalaIdOrderByTimestampAsc(salaId);
        return ResponseEntity.ok(history);
    }
}