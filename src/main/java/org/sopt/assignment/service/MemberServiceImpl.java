package org.sopt.assignment.service;

import org.sopt.assignment.domain.Gender;
import org.sopt.assignment.domain.Member;
import org.sopt.assignment.exception.BaseException;
import org.sopt.assignment.exception.ErrorCode;
import org.sopt.assignment.repository.MemoryMemberRepository;
import org.sopt.assignment.util.IdGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MemberServiceImpl implements MemberService {

    private final MemoryMemberRepository memberRepository;

    public MemberServiceImpl(MemoryMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(String name, String email, LocalDate birthday, Gender gender) {

        if(existsMemberByEmail(email)){
            throw BaseException.type(ErrorCode.NOT_DUPLICATED_EMAIL);
        }

        Long id = IdGenerator.generateMemberId();
        Member member = Member.create(id, name, email, birthday, gender);

        memberRepository.save(member);
        return member.getId();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> BaseException.type(ErrorCode.NOT_FOUND_MEMBER));
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public String delete(Long memberId) {

        Member member = findOne(memberId);

        return memberRepository.delete(memberId);
    }

    private boolean existsMemberByEmail(String email) {
        return memberRepository.existsMemberByEmail(email); }

}