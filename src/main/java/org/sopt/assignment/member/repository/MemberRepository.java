package org.sopt.assignment.member.repository;

import org.sopt.assignment.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(Long id);

    List<Member> findAll();

    boolean existsMemberByEmail(String email);

    String delete(Long memberId);
}
