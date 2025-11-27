package org.sopt.assignment.member.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.assignment.member.dto.response.GetAllMembersResponseDto;
import org.sopt.assignment.member.dto.response.MemberResponseDto;
import org.sopt.assignment.member.service.MemberService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/{memberId}")
    public MemberResponseDto findMember(@PathVariable Long memberId) {
        return memberService.findMember(memberId);
    }

    @GetMapping("/members/all")
    public GetAllMembersResponseDto getAllMembers() {
        return memberService.getAllMembers();
    }

    @DeleteMapping("/members/{memberId}")
    public MemberResponseDto deleteMember(@PathVariable Long memberId){
        return memberService.deleteMember(memberId);
    }
}