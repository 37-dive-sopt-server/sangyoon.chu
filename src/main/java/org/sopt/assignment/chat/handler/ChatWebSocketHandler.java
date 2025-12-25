package org.sopt.assignment.chat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.assignment.chat.dto.ChatMessage;
import org.sopt.assignment.global.exception.BaseException;
import org.sopt.assignment.global.exception.CommonErrorCode;
import org.sopt.assignment.member.domain.Member;
import org.sopt.assignment.member.exception.MemberErrorCode;
import org.sopt.assignment.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        log.info("새로운 연결: {}, 현재 접속자: {}", session.getId(), sessions.size());

        String sender = getSenderName(session);
        ChatMessage enterMessage = ChatMessage.of(
                ChatMessage.MessageType.ENTER,
                sender,
                sender + "님이 입장했습니다."
        );
        broadcast(enterMessage);
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("받은 메시지: {} from {}", payload, session.getId());

        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);

        if(chatMessage.sender() == null || chatMessage.sender().isEmpty()) {
            String sender = getSenderName(session);
            chatMessage = ChatMessage.of(
                    chatMessage.type(),
                    sender,
                    chatMessage.content()
            );
        }

        broadcast(chatMessage);
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        log.info("연결 종료: {}, 현재 접속자: {}", session.getId(), sessions.size());

        String sender = getSenderName(session);
        ChatMessage leaveMessage = ChatMessage.of(
                ChatMessage.MessageType.LEAVE,
                sender,
                sender + "님이 퇴장했습니다."
        );
        broadcast(leaveMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("에러 발생: {}", session.getId(), exception);
        sessions.remove(session);
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

    private String getSenderName(WebSocketSession session) {
        Long userId = (Long) session.getAttributes().get("userId");

        if(userId == null) {
            throw  BaseException.type(CommonErrorCode.INVALID_JWT);
        }
        return memberRepository.findById(userId)
                .map(Member::getName)
                .orElseThrow(() -> BaseException.type(MemberErrorCode.NOT_FOUND_MEMBER));

    }
}
