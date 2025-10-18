package org.sopt.assignment.exception;

import java.time.format.DateTimeParseException;

public class ExceptionHandler {

    public static void handle(Exception e) {
        switch (e) {
            case BaseException baseEx -> {
                ErrorCode errorCode = baseEx.getErrorCode();
                System.out.println("❌ [" + errorCode.getCode() + "] " + errorCode.getMessage());
            }
            case DateTimeParseException dateTimeParseException ->
                    System.out.println("❌ 잘못된 날짜 형식입니다. yyyy-MM-dd 형식으로 입력해주세요.");
            case NumberFormatException numberFormatException -> System.out.println("❌ 유효하지 않은 ID 형식입니다. 숫자를 입력해주세요.");
            default -> System.out.println("❌ 알 수 없는 오류가 발생했습니다: " + e.getMessage());
        }
    }
}