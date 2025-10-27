package org.sopt.assignment.service;

import org.sopt.assignment.domain.Gender;
import org.sopt.assignment.domain.Member;
import org.sopt.assignment.dto.request.CreateMemberRequestDto;

import java.time.LocalDate;
import java.util.List;

public interface MemberService {

    Long join(CreateMemberRequestDto request);

    Member findOne(Long memberId);

    List<Member> findAllMembers();

    String delete(Long memberId);
}
