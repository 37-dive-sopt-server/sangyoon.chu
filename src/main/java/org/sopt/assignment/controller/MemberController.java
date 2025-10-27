package org.sopt.assignment.controller;

import org.sopt.assignment.dto.request.CreateMemberRequestDto;
import org.sopt.assignment.dto.response.MemberResponseDto;
import org.sopt.assignment.dto.response.GetAllMembersResponseDto;
import org.sopt.assignment.service.MemberService;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public MemberResponseDto createMember(@RequestBody CreateMemberRequestDto request) {
        return memberService.join(request);
    }

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