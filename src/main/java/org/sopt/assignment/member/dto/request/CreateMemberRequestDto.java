package org.sopt.assignment.member.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.assignment.member.domain.EGender;

import java.time.LocalDate;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record CreateMemberRequestDto(

        @JsonProperty("name")
        @Schema(description = "등록 사용자 이름", example = "추상윤", requiredMode = REQUIRED)
        String name,

        @JsonProperty("email")
        @Schema(description = "등록 사용자 이메일", example = "dhzktldh@gmail.com", requiredMode = REQUIRED)
        String email,

        @JsonProperty("birthday")
        @Schema(description = "등록 사용자 생년월일", example = "2003-03-03", requiredMode = REQUIRED)
        LocalDate birthday,

        @JsonProperty("gender")
        @Schema(description = "등록 사용자 성별(MALE or FEMALE", example = "MALE", requiredMode = REQUIRED)
        EGender gender,

        @JsonProperty("password")
        @Schema(example = "password123!")
        String password
) {
}
