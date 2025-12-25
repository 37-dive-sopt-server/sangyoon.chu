package org.sopt.assignment.global.config;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.sopt.assignment.chat.handler.ChatWebSocketHandler;
import org.sopt.assignment.global.security.info.JwtUserInfo;
import org.sopt.assignment.global.security.util.JwtUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatWebSocketHandler chatWebSocketHandler;
    private final JwtUtil jwtUtil;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWebSocketHandler, "/ws/chat")
                .addInterceptors(new JwtHandshakeInterceptor(jwtUtil))
                .setAllowedOrigins("*");
    }

    @RequiredArgsConstructor
    private static class JwtHandshakeInterceptor implements HandshakeInterceptor {

        private final JwtUtil jwtUtil;

        @Override
        public boolean beforeHandshake(ServerHttpRequest request,
                                       ServerHttpResponse response,
                                       WebSocketHandler wsHandler,
                                       Map<String, Object> attributes) throws Exception {
            if(request instanceof ServletServerHttpRequest){
                ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;

                String token = servletRequest.getServletRequest().getParameter("token");

                if(token != null){
                    try{
                        Claims claims = jwtUtil.validateToken(token);
                        JwtUserInfo jwtUserInfo = JwtUserInfo.from(claims);

                        attributes.put("userId", jwtUserInfo.userId());
                        attributes.put("token", token);

                        return true;
                    } catch (Exception e){
                        return false;
                    }
                }

            }

            return false;
        }

        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

        }
    }
}
