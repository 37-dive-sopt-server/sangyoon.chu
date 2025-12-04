package org.sopt.assignment.global.security.info;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONValue;
import org.sopt.assignment.global.exception.ErrorCode;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class AuthenticationResponse {

    public static void makeFailureResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getStatus().value());

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", errorCode.getStatus().value());
        body.put("errorCode", errorCode.getErrorCode());
        body.put("message", errorCode.getMessage());

        response.getWriter().write(JSONValue.toJSONString(body));
    }
}