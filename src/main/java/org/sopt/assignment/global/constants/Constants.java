package org.sopt.assignment.global.constants;

import java.util.List;
import java.util.regex.Pattern;

public class Constants {
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public static List<String> NO_NEED_AUTH = List.of(
            "/swagger",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/api-docs",
            "/api-docs/**",
            "/v3/api-docs/**",
            "/api/health",
            "/api/health-check"
    );
}
