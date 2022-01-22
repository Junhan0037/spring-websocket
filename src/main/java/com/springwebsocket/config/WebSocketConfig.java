package com.springwebsocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // WebSocket 서버를 활성화하는 데 사용
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 클라이언트가 웹 소켓 서버에 연결하는 데 사용할 웹 소켓 엔드 포인트를 등록
     * 엔드 포인트 구성에 withSockJS ()를 사용
     * SockJS는 웹 소켓을 지원하지 않는 브라우저에 폴백 옵션을 활성화하는 데 사용
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

    /**
     * 한 클라이언트에서 다른 클라이언트로 메시지를 라우팅 하는 데 사용될 메시지 브로커를 구성
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // "/app" 시작되는 메시지가 message-handling methods 로 라우팅 되어야 한다는 것을 명시
        registry.setApplicationDestinationPrefixes("/app");

        // "/topic" 시작되는 메시지가 메시지 브로커로 라우팅 되도록 정의
        // 메시지 브로커는 특정 주제를 구독 한 연결된 모든 클라이언트에게 메시지를 broadcast
        // 인 메모리 브로커 활성화 (RabbitMQ, ActiveMQ 같은 메시지 브로커 사용 가능)
        registry.enableSimpleBroker("/topic");
    }

}
