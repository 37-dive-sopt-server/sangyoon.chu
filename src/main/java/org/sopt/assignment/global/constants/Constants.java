package org.sopt.assignment.global.constants;

import java.util.List;
import java.util.regex.Pattern;

public class Constants {
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    public static final String PREFIX_AUTH = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String CLAIM_USER_ID = "userId";
    public static final String CLAIM_USER_ROLE = "role";
    public static final String CHAT_MESSAGES_KEY = "chat:messages";
    public static final int MAX_MESSAGE_COUNT = 100;
    public static final String CONNECTED_USERS_KEY = "chat:connected_users";

    public static List<String> NO_NEED_AUTH = List.of(
            "/swagger",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/api-docs",
            "/api-docs/**",
            "/v3/api-docs/**",
            "/api/health",
            "/api/health-check",
            "/api/v1/login",
            "/api/v1/members",
            "/chat.html",
            "/ws/chat"
    );
}
