package org.sopt.assignment.service;

import org.sopt.assignment.domain.Gender;
import org.sopt.assignment.domain.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberService {

    Long join(String name, String email, LocalDate birthday, Gender gender);

    Member findOne(Long memberId);

    List<Member> findAllMembers();

    String delete(Long memberId);
}
