package org.sopt.assignment.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.assignment.domain.Gender;
import org.sopt.assignment.domain.Member;

import java.time.LocalDate;

public record MemberResponseDto(
        @JsonProperty("id")
        @Schema(description = "대상이 되는 사용자 아이디", example = "1")
        Long id,

        @JsonProperty("name")
        @Schema(description = "대상이 되는 사용자 이름", example = "추상윤")
        String name,

        @JsonProperty("email")
        @Schema(description = "대상이 되는 사용자 이메일", example = "dhzktldh@gmail.com")
        String email,

        @JsonProperty("birthday")
        @Schema(description = "대상이 되는 사용자 생년월일", example = "2003-03-03")
        LocalDate birthday,

        @JsonProperty("gender")
        @Schema(description = "대상이 되는 사용자 아이디", example = "MALE")
        Gender gender
) {

    public static MemberResponseDto from(Member member) {
        return new MemberResponseDto(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getBirthday(),
                member.getGender());
    }
}
