package org.sopt.assignment.member.repository;

import org.sopt.assignment.member.domain.Member;
import org.sopt.assignment.member.dto.MemberSecurityForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsMemberByEmail(String email);

    @Query("SELECT m.id as userId, " +
            "       m.password as password, " +
            "       m.role as role " +
            "FROM Member m " +
            "WHERE m.id = :id")
    Optional<MemberSecurityForm> findMemberSecurityFormById(@Param("id") Long id);

    Optional<Member> findByEmail(String email);

}
