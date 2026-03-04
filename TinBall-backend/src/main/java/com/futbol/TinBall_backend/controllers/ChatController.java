package com.futbol.TinBall_backend.controllers;

import com.futbol.TinBall_backend.models.ChatMessage;
import com.futbol.TinBall_backend.repositories.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // 1. Inyectamos el repositorio
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        
        // 2. Le asignamos la hora exacta del servidor para evitar problemas de zonas horarias con el frontend
        chatMessage.setTimestamp(LocalDateTime.now());

        // 3. Guardamos el mensaje en la base de datos SQL
        chatMessageRepository.save(chatMessage);

        // 4. Retransmitimos el mensaje al canal del partido
        String destination = "/topic/match/" + chatMessage.getMatchId();
        messagingTemplate.convertAndSend(destination, chatMessage);
    }
}