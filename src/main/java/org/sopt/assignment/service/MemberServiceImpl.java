package org.sopt.assignment.service;

import org.sopt.assignment.domain.Gender;
import org.sopt.assignment.domain.Member;
import org.sopt.assignment.repository.MemoryMemberRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MemberServiceImpl implements MemberService {

    private final MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    private static long sequence = 1L;

    public Long join(String name, String email, LocalDate birthday, Gender gender) {
        Member member = new Member(sequence++, name, email, birthday, gender);
        memberRepository.save(member);
        return member.getId();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public boolean existsMemberByEmail(String email) {
        return memberRepository.existsMemberByEmail(email); }

    public String delete(Long memberId) {
        return memberRepository.delete(memberId);
    }
}