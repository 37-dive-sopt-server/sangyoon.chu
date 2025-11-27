package org.sopt.assignment.global.security.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sopt.assignment.global.exception.CommonErrorCode;
import org.sopt.assignment.global.exception.ErrorCode;
import org.sopt.assignment.global.security.info.AuthenticationResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPointerHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = (ErrorCode) request.getAttribute("errorCode");
        if (errorCode == null) {
            AuthenticationResponse.makeFailureResponse(response, CommonErrorCode.VALIDATION_ERROR);
            return ;
        }
        AuthenticationResponse.makeFailureResponse(response, errorCode);
    }
}
