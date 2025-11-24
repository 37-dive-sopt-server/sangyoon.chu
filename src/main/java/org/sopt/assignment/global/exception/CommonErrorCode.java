package org.sopt.assignment.global.exception;

import org.springframework.http.HttpStatus;

public enum CommonErrorCode implements ErrorCode {

    // ==== 인증 에러 (4xx) ====
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "AUTH_001", "유효하지 않은 JWT입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_002", "JWT 토큰이 일치하지 않습니다"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_003", "자격 증명이 이루어지지 않았습니다."),
    INVALID_USER(HttpStatus.FORBIDDEN,"AUTH_004","권한이 없는 유저의 접근입니다."),


    // ===== 공통 에러 (4xx) =====
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "COMMON_001", "입력값 검증에 실패했습니다."),
    NOT_SUPPORTED_METHOD_ERROR(HttpStatus.METHOD_NOT_ALLOWED, "COMMON_002", "지원하지 않는 HTTP 메서드입니다."),
    NOT_SUPPORTED_URI_ERROR(HttpStatus.NOT_FOUND, "COMMON_003", "지원하지 않는 URI입니다."),
    NOT_SUPPORTED_MEDIA_TYPE_ERROR(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "COMMON_004", "지원하지 않는 미디어 타입입니다."),


    // ===== 서버 에러 (5xx) =====
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER_001", "서버 내부 오류가 발생했습니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;

    CommonErrorCode (HttpStatus httpStatus, String errorCode, String message){
        this.status = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
