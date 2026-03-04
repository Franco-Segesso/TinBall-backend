package com.futbol.TinBall_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Habilita un broker en memoria para los canales de suscripción (lo que escucha React)
        config.enableSimpleBroker("/topic");
        // Prefijo para los mensajes que React envía hacia Java
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // El endpoint al que React se conecta inicialmente. 
        // setAllowedOriginPatterns evita errores de CORS durante el desarrollo.
        registry.addEndpoint("/ws-tinball")
                .setAllowedOriginPatterns("*") 
                .withSockJS();
    }
}