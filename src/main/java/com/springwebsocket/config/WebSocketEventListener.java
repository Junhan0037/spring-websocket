package com.springwebsocket.config;

import com.springwebsocket.model.ChatMessage;
import com.springwebsocket.model.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * event listener 를 이용하여 소켓 연결(socket connect) 그리고 소켓 연결 끊기(disconnect) 이벤트를 수신하여
 * 사용자가 채팅방을 참여(JOIN)하거나 떠날때(LEAVE)의 이벤트를 logging 하거나 broadcast
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * Spring 4.2부터는 이벤트 리스너가 ApplicationListener 인터페이스를 구현하는 Bean 일 필요가 없어졌습니다
     * @EventListener 주석을 통해 관리되는 Bean 의 모든 public 메소드에 등록 할 수 있습니다.
     * SessionConnectedEvent, SessionDisconnectEvent 상속관계를 거슬로 올라가다 보면 ApplicationEvent 를 상속
     * Spring 4.2 부터는 ApplicationEvent 를 상속받지 않는 POJO 클래스 로도 이벤트로 사용가능
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            log.info("User Disconnected : " + username);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(MessageType.LEAVE);
            chatMessage.setSender(username);

            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }

}
