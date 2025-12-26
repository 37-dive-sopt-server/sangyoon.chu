package org.sopt.assignment.chat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.assignment.chat.domain.ChatMessage;
import org.sopt.assignment.chat.service.ChatService;
import org.sopt.assignment.chat.service.ChatSessionManager;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    private final ChatSessionManager chatSessionManager;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);

        Long memberId = getUserId(session);
        log.info("새로운 연결: {}, 현재 접속자: {}", session.getId(), sessions.size());

        chatSessionManager.addUser(memberId, session.getId());

        ChatMessage enterMessage = chatService.createEnterMessage(memberId);
        broadcast(enterMessage);
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("받은 메시지: {} from {}", payload, session.getId());

        Long memberId = getUserId(session);

        ChatMessage chatMessage = chatService.processMessage(payload, memberId);

        broadcast(chatMessage);
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        log.info("연결 종료: {}, 현재 접속자: {}", session.getId(), sessions.size());

        Long memberId = getUserId(session);
        ChatMessage leaveMessage = chatService.createLeaveMessage(memberId);
        broadcast(leaveMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("에러 발생: {}", session.getId(), exception);
        if (!session.isOpen()) {
            log.warn("세션이 실제로 닫혔음, 제거 처리: sessionId={}", session.getId());
            sessions.remove(session);
            chatSessionManager.removeUserBySessionId(session.getId());
        }
    }

    private void broadcast(ChatMessage message) {
        sessions.forEach(session -> {
            try{
                String json =  objectMapper.writeValueAsString(message);
                session.sendMessage(new TextMessage(json));
            } catch (IOException e){
                log.error("메시지 전송 실패: {}", session.getId(), e);
            }
        });
    }

    private Long getUserId(WebSocketSession session) {
        return (Long) session.getAttributes().get("userId");
    }
}
