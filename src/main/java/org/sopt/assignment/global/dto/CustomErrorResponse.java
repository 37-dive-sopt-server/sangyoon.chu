package org.sopt.assignment.global.dto;

import org.sopt.assignment.global.exception.ErrorCode;

public record CustomErrorResponse (

        int status,
        String errorCode,
        String message

) {

    public static CustomErrorResponse from(ErrorCode code) {
        return new CustomErrorResponse(
                code.getStatus().value(),
                code.getErrorCode(),
                code.getMessage()
        );
    }

    public static CustomErrorResponse of(ErrorCode errorCode, String message) {
        return new CustomErrorResponse(
                errorCode.getStatus().value(),
                errorCode.getErrorCode(),
                message
        );
    }
}