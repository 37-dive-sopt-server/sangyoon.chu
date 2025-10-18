package org.sopt.assignment.validator;

import org.sopt.assignment.exception.BaseException;
import org.sopt.assignment.exception.ErrorCode;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class MemberInputValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private MemberInputValidator() {}

    public static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw BaseException.type(ErrorCode.NOT_EMPTY_NAME);
        }
    }

    public static void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw BaseException.type(ErrorCode.NOT_EMPTY_EMAIL);
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw BaseException.type(ErrorCode.INVALID_EMAIL);
        }
    }

    public static void validateBirthday(LocalDate birthday) {
        if (birthday == null) {
            throw BaseException.type(ErrorCode.NOT_EMPTY_BIRTHDAY);
        }
    }
}