package org.sopt.assignment.member.domain;

import lombok.Getter;

@Getter
public enum ERole {
    USER("일반 유저"),
    ADMIN("관리자");


    ERole(String description) {
        this.description = description;
    }
    private final String description;

}
