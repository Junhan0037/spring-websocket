package com.springwebsocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // Stomp 사용
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    /**
     * pub/sub 메시징을 구현하기 위해
     * 메시지를 발행하는 요청의 prefix는 "/pub"으로 시작하도록 설정
     * 메세지를 구독하는 요청의 prefix는 "/sub"로 시작하도록 설정
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/pub");
    }

    /**
     * Stomp WebSocket의 연결 endpoint는 "/ws-stomp"로 설정
     * 따라서 개발서버의 접속 주소는 다음과 같이 됩니다 "ws://localhost:8080/ws-stomp"
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*").withSockJS();
    }

}
