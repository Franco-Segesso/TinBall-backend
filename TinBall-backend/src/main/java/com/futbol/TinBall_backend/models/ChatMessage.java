package com.futbol.TinBall_backend.models; // Asegurate de que coincida con tu carpeta

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long matchId;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false, length = 1000)
    private String content;

    private LocalDateTime timestamp;

    // Constructor vacío (obligatorio para Spring/JPA)
    public ChatMessage() {
    }

    // Constructor con parámetros
    public ChatMessage(Long matchId, String sender, String content, LocalDateTime timestamp) {
        this.matchId = matchId;
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }

    // --- GETTERS Y SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMatchId() { return matchId; }
    public void setMatchId(Long matchId) { this.matchId = matchId; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}