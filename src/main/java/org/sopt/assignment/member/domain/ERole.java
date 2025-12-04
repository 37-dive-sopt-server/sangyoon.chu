package org.sopt.assignment.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ERole {
    USER("ROLE_USER", "일반 유저"),
    ADMIN("ROLE_ADMIN", "관리자");


    private final String securityRole;
    private final String description;
}
