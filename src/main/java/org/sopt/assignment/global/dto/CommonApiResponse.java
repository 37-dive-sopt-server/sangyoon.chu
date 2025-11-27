package org.sopt.assignment.global.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"errorCode", "message", "result"})
public record CommonApiResponse<T> (

        String errorCode,
        String message,
         T result
){
    public static <T> CommonApiResponse<T> success(T data) {
        return new CommonApiResponse<>(null, "OK", data);
    }
}