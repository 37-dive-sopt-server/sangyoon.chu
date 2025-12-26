package org.sopt.assignment.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.assignment.chat.domain.EMessageType;
import org.sopt.assignment.chat.domain.ChatMessage;
import org.sopt.assignment.chat.exception.ChatErrorCode;
import org.sopt.assignment.global.constants.Constants;
import org.sopt.assignment.global.exception.BaseException;
import org.sopt.assignment.member.service.MemberService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.sopt.assignment.global.constants.Constants.CHAT_MESSAGES_KEY;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final MemberService memberService;

    public ChatMessage processMessage(String payload, Long memberId){
        try{
            ChatMessage message = objectMapper.readValue(payload, ChatMessage.class);

            String memberName = memberService.getMemberById(memberId).getName();

            ChatMessage chatMessage = ChatMessage.of(message.type(), memberName, message.content());

            if(message.type() == EMessageType.CHAT){
                saveMessage(chatMessage);
            }

            return chatMessage;
        } catch (JsonProcessingException e){
            log.error("메시지 저장 실패", e);
            throw BaseException.type(ChatErrorCode.FAILED_SAVE_MESSAGE);
        }

    }


    public ChatMessage createEnterMessage(Long memberId) {
        String memberName = memberService.getMemberById(memberId).getName();

        return ChatMessage.of(
                EMessageType.ENTER,
                memberName,
                memberName + "님이 입장했습니다."
        );
    }

    // 퇴장 메시지 생성
    public ChatMessage createLeaveMessage(Long memberId) {
        String memberName = memberService.getMemberById(memberId).getName();

        return ChatMessage.of(
                EMessageType.LEAVE,
                memberName,
                memberName + "님이 퇴장했습니다."
        );
    }

    public void saveMessage(ChatMessage message) {
        try {
            String json = objectMapper.writeValueAsString(message);

            redisTemplate.opsForList().leftPush(CHAT_MESSAGES_KEY, json);

            redisTemplate.opsForList().trim(CHAT_MESSAGES_KEY, 0, Constants.MAX_MESSAGE_COUNT - 1);

            log.info("Redis 저장: {}", message.content());
        } catch (JsonProcessingException e){
            log.error("메시지 저장 실패", e);
        }
    }

    public List<ChatMessage> getRecentMessages(int count) {
        List<String> messages = redisTemplate.opsForList().range(
                CHAT_MESSAGES_KEY,
                0,
                count - 1
        );

        List<ChatMessage> result = new ArrayList<>();
        if (messages != null) {
            for (String json : messages) {
                try {
                    result.add(objectMapper.readValue(json, ChatMessage.class));
                } catch (JsonProcessingException e) {
                    log.error("메시지 파싱 실패: {}", json, e);
                }
            }
        }

        return result;
    }

}
