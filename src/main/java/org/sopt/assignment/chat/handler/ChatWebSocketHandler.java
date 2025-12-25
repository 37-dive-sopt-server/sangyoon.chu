package org.sopt.assignment.chat.handler;

import lombok.extern.slf4j.Slf4j;
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
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        log.info("새로운 연결: {}, 현재 접속자: {}", session.getId(), sessions.size());
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("받은 메시지: {} from {}", payload, session.getId());

        broadcast(message);
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        log.info("연결 종료: {}, 현재 접속자: {}", session.getId(), sessions.size());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("에러 발생: {}", session.getId(), exception);
        sessions.remove(session);
    }

    private void broadcast(TextMessage message) {
        sessions.forEach(session -> {
            try{
                session.sendMessage(message);
            } catch (IOException e){
                log.error("메시지 전송 실패: {}", session.getId(), e);
            }
        });
    }
}
