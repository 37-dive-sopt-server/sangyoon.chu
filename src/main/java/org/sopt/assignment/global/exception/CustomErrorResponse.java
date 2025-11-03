package org.sopt.assignment.global.exception;


public class CustomErrorResponse {

    private int status;
    private String errorCode;
    private String message;


    private CustomErrorResponse(ErrorCode code){
        this.status = code.getStatus().value();
        this.errorCode = code.getErrorCode();
        this.message = code.getMessage();
    }

    public CustomErrorResponse(int status, String errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public static CustomErrorResponse from(ErrorCode code){
        return new CustomErrorResponse(code);
    }

    public static CustomErrorResponse of(ErrorCode errorCode, String message){
        return new CustomErrorResponse(errorCode.getStatus().value(), errorCode.getErrorCode(), message);
    }

    public int getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}