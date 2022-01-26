package com.springwebsocket.model;

import com.springwebsocket.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoom {

    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        // 입장 시에는 채팅룸의 session정보에 클라리언트의 session리스트에 추가
        if (chatMessage.getType() == MessageType.ENTER) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
        }

        // 메시지 발송
        sendMessage(chatMessage, chatService);
    }

    /**
     * 채팅룸의 모든 session에 메시지를 발송
     */
    private <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }

}
