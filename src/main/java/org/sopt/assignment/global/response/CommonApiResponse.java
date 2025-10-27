package org.sopt.assignment.global.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"errorCode", "message", "result"})
public class CommonApiResponse<T> {

    private String errorCode;
    private String message;
    private T result;

    public static <T> CommonApiResponse<T> success(T data) {
        return new CommonApiResponse<>(null, "OK", data);
    }

    private CommonApiResponse(String errorCode, String message, T result) {
        this.errorCode = errorCode;
        this.message = message;
        this.result = result;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public T getResult() {
        return this.result;
    }
}