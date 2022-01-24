package com.springwebsocket.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Handler 를 이용하여 웹소켓을 활성화 하기 위한 Config 파일 입니다.
 * 웹소켓에 접속하기 위한 endpoint 는 "/ws/chat"으로 설정하고 도메인이 다른 서버에서도 접속 가능하도록 CORS: setAllowedOrigins("*")를 설정
 */
@Configuration
@EnableWebSocket // 웹소켓을 활성화
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketChatHandler webSocketChatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketChatHandler, "/ws/chat").setAllowedOrigins("*");
    }

}
