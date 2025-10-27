package org.sopt.assignment.service;

import org.sopt.assignment.dto.request.CreateMemberRequestDto;
import org.sopt.assignment.dto.response.MemberResponseDto;
import org.sopt.assignment.dto.response.GetAllMembersResponseDto;

public interface MemberService {

    MemberResponseDto join(CreateMemberRequestDto request);

    MemberResponseDto findMember(Long memberId);

    GetAllMembersResponseDto getAllMembers();

    MemberResponseDto deleteMember(Long memberId);
}
