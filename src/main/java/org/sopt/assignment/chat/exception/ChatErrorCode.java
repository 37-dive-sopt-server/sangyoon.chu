package org.sopt.assignment.chat.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.assignment.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ChatErrorCode implements ErrorCode {

    FAILED_SAVE_MESSAGE(HttpStatus.INTERNAL_SERVER_ERROR, "CHAT_001", "메시지 저장에 실패했습니다."),;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;

}
