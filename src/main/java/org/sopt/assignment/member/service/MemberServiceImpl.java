package org.sopt.assignment.member.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.assignment.global.exception.BaseException;
import org.sopt.assignment.member.domain.Member;
import org.sopt.assignment.member.dto.response.GetAllMembersResponseDto;
import org.sopt.assignment.member.dto.response.MemberResponseDto;
import org.sopt.assignment.member.exception.MemberErrorCode;
import org.sopt.assignment.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private static final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public MemberResponseDto findMember(Long memberId) {

        Member member = getMemberById(memberId);

        return MemberResponseDto.from(member);
    }

    public GetAllMembersResponseDto getAllMembers() {
        log.info("회원 전체 조회 프로세스 시작");

        List<Member> members = memberRepository.findAll();
        log.info("회원 전체 조회 완료 - 총 {}명", members.size());
        return GetAllMembersResponseDto.from(members.stream()
                .map(MemberResponseDto::from).toList());
    }

    public MemberResponseDto deleteMember(Long memberId) {
        log.info("회원 삭제 프로세스 시작 - memberId: {}", memberId);

        Member member = getMemberById(memberId);

        memberRepository.delete(member);

        log.info("회원 삭제 완료 - memberId: {}", memberId);
        return MemberResponseDto.from(member);
    }

    public Member getMemberById(Long memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.warn("멤버를 찾을 수 없습니다 - memberId={}", memberId);
                    return BaseException.type(MemberErrorCode.NOT_FOUND_MEMBER);
                });
    }

}