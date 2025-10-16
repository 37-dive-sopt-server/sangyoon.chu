package org.sopt.assignment.exception;

public class ExceptionHandler {

    public static void handle(Exception e) {
        if (e instanceof BaseException baseEx) {
            ErrorCode errorCode = baseEx.getErrorCode();
            System.out.println("❌ [" + errorCode.getCode() + "] " + errorCode.getMessage());
        }
    }
}