package org.sopt.assignment.service;

import org.sopt.assignment.domain.Member;
import org.sopt.assignment.dto.request.CreateMemberRequestDto;
import org.sopt.assignment.dto.response.MemberResponseDto;
import org.sopt.assignment.dto.response.GetAllMembersResponseDto;
import org.sopt.assignment.exception.BaseException;
import org.sopt.assignment.exception.ErrorCode;
import org.sopt.assignment.repository.MemberRepository;
import org.sopt.assignment.util.IdGenerator;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponseDto join(CreateMemberRequestDto request) {

        if(existsMemberByEmail(request.email())){
            throw BaseException.type(ErrorCode.NOT_DUPLICATED_EMAIL);
        }

        Member.validateCreation(request.name(), request.email(), request.birthday(), request.gender());

        Long id = IdGenerator.generateMemberId();
        Member member = Member.create(id, request.name(), request.email(), request.birthday(), request.gender());

        memberRepository.save(member);

        return MemberResponseDto.from(member);
    }

    public MemberResponseDto findMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                            .orElseThrow(() -> BaseException.type(ErrorCode.NOT_FOUND_MEMBER));

        return MemberResponseDto.from(member);
    }

    public GetAllMembersResponseDto getAllMembers() {
        return GetAllMembersResponseDto.from(memberRepository.findAll().stream()
                .map(MemberResponseDto::from).toList());
    }

    public MemberResponseDto deleteMember(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> BaseException.type(ErrorCode.NOT_FOUND_MEMBER));

        memberRepository.delete(memberId);

        return MemberResponseDto.from(member);
    }

    private boolean existsMemberByEmail(String email) {
        return memberRepository.existsMemberByEmail(email); }

}