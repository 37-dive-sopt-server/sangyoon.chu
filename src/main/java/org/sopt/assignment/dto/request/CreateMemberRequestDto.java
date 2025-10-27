package org.sopt.assignment.dto.request;

import org.sopt.assignment.domain.Gender;

import java.time.LocalDate;

public record CreateMemberRequestDto(
        String name,

        String email,

        LocalDate birthday,

        Gender gender
) {
}
