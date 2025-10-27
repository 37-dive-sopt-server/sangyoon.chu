package org.sopt.assignment.service;

import org.sopt.assignment.domain.Gender;
import org.sopt.assignment.domain.Member;
import org.sopt.assignment.dto.request.CreateMemberRequestDto;
import org.sopt.assignment.exception.BaseException;
import org.sopt.assignment.exception.ErrorCode;
import org.sopt.assignment.repository.MemberRepository;
import org.sopt.assignment.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(CreateMemberRequestDto request) {

        if(existsMemberByEmail(request.email())){
            throw BaseException.type(ErrorCode.NOT_DUPLICATED_EMAIL);
        }

        Member.validateCreation(request.name(), request.email(), request.birthday(), request.gender());

        Long id = IdGenerator.generateMemberId();
        Member member = Member.create(id, request.name(), request.email(), request.birthday(), request.gender());

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