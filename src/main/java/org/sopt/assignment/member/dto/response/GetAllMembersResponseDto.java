package org.sopt.assignment.member.dto.response;

import java.util.List;

public record GetAllMembersResponseDto(
        List<MemberResponseDto> findMemberResponseList
) {
    public static GetAllMembersResponseDto from(List<MemberResponseDto> findMemberResponseList) {
        return new GetAllMembersResponseDto(findMemberResponseList);
    }
}
