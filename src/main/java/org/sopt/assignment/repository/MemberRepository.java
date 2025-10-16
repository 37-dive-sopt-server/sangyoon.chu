package org.sopt.assignment.repository;

import org.sopt.assignment.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(Long id);

    List<Member> findAll();

    boolean existsMemberByEmail(String email);

    String delete(Long memberId);
}
