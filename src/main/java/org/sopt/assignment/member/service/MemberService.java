package org.sopt.assignment.member.service;

import org.sopt.assignment.member.domain.Member;
import org.sopt.assignment.member.dto.request.CreateMemberRequestDto;
import org.sopt.assignment.member.dto.response.MemberResponseDto;
import org.sopt.assignment.member.dto.response.GetAllMembersResponseDto;

public interface MemberService {

    MemberResponseDto join(CreateMemberRequestDto request);

    MemberResponseDto findMember(Long memberId);

    GetAllMembersResponseDto getAllMembers();

    MemberResponseDto deleteMember(Long memberId);

    Member getMemberById(Long memberId);
}
