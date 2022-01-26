package com.springwebsocket.controller;

import com.springwebsocket.model.ChatMessage;
import com.springwebsocket.model.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * WebSocket으로 들어오는 메시지 발행을 처리
     * 클라이언트에서는 prefix를 붙여서 "/pub/chat/message"로 발행 요청을 하면 Controller가 해당 메시지를 받아 처리
     * 메시지가 발행되면 "/sub/chat/room/{roomId}"로 메시지를 send
     * 구독자 구현은 서버단에서 구현할 필요가 없습니다. Front에서 STOMP 라이브러리를 이용해서 subscriber 주소를 바라보고 있는 코드만 작성하면 됩니다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (message.getType() == MessageType.ENTER) {
            message.setMessage(message.getSender() + "님이 입장하였습니다.");
        }
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

}
