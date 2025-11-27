package org.sopt.assignment.auth.dto.request;

import org.sopt.assignment.member.domain.EGender;

import java.time.LocalDate;

public record JoinCommandDto(
        String name,

        String email,

        LocalDate birthday,

        EGender gender,

        String password
) {
    public  static JoinCommandDto from(JoinRequestDto request) {
        return new JoinCommandDto(
                request.name(),
                request.email(),
                request.birthday(),
                request.gender(),
                request.password()
        );
    }
}
