package org.sopt.assignment.member.domain;

import org.sopt.assignment.global.util.exception.BaseException;
import org.sopt.assignment.member.exception.MemberErrorCode;

public enum Gender {
    MALE("남자"), FEMALE("여자");

    Gender(String description) {
        this.description = description;
    }

    private final String description;

    public static Gender fromInput(String input){
        return switch (input){
            case "1" -> MALE;
            case "2" -> FEMALE;
            default -> throw BaseException.type(MemberErrorCode.INVALID_GENDER);
        };
    }

    public String getDescription() {
        return description;
    }
}
