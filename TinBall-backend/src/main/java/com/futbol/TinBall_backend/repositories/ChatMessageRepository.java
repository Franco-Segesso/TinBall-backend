package com.futbol.TinBall_backend.repositories;

import com.futbol.TinBall_backend.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    
    // Magia de Spring Boot: Con solo nombrar el método así, Java ya sabe que tiene 
    // que hacer un "SELECT * FROM chat_messages WHERE match_id = ? ORDER BY timestamp ASC"
    List<ChatMessage> findByMatchIdOrderByTimestampAsc(Long matchId);
}