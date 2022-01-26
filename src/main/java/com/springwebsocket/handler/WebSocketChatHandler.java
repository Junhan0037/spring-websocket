package com.springwebsocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springwebsocket.model.ChatMessage;
import com.springwebsocket.model.ChatRoom;
import com.springwebsocket.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Socket 통신은 서버와 클라이언트가 1:N으로 관계를 맺습니다. 따라서 한 서버에 여러 클라이언트가 접속할 수 있습니다.
 * 서버에는 여러 클라이언트가 발송한 메세지를 받아 처리해줄 Handler 의 작성이 필요합니다.
 * TextWebSocketHandler 룰 상속받아 Handler 를 작성해 줍니다.
 * Client 로부터 받은 메세지를 Console Log 에 출력하고 Client 로 환영 메시지를 보내는 역할을 합니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        room.handleActions(session, chatMessage, chatService);
    }

}
