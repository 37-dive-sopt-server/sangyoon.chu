package org.sopt.assignment.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.assignment.chat.domain.ConnectedUser;
import org.sopt.assignment.member.service.MemberService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.sopt.assignment.global.constants.Constants.CONNECTED_USERS_KEY;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatSessionManager {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final MemberService memberService;
    private final Map<String, Long> sessionUserMap = new ConcurrentHashMap<>();


    public void addUser(Long userId, String sessionId){
        String userName = memberService.getMemberById(userId).getName();

        try{
            sessionUserMap.put(sessionId, userId);
            ConnectedUser user = ConnectedUser.of(userId, userName, sessionId);
            String json = objectMapper.writeValueAsString(user);

            redisTemplate.opsForHash().put(CONNECTED_USERS_KEY, userId.toString(), json);

            log.info("접속자 추가: userId = {}, userName = {}, 현재 접속자 = {}",  userId, userName, getConnectedUserCount());
        } catch (JsonProcessingException e){
            log.error("사용자 추가 실패", e);
        }
    }

    public void removeUserBySessionId(String sessionId){
        Long userId = sessionUserMap.get(sessionId);
        if(userId != null){
            redisTemplate.opsForHash().delete(CONNECTED_USERS_KEY, userId.toString());
            sessionUserMap.remove(sessionId);

            log.info("접속자 제거: userId = {}, sessionId = {}", userId, sessionId);
        }
    }

    public List<ConnectedUser> getConnectedUsers(){
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(CONNECTED_USERS_KEY);
        List<ConnectedUser> users = new ArrayList<>();

        for (Object value : entries.values()) {
            try{
                users.add(objectMapper.readValue((String) value, ConnectedUser.class));
            } catch (JsonProcessingException e){
                log.error("사용자 파싱 실패", e);
            }
        }
        return users;
    }

    public long getConnectedUserCount() {
        return redisTemplate.opsForHash().size(CONNECTED_USERS_KEY);
    }
}
