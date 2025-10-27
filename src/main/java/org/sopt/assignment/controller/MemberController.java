package org.sopt.assignment.controller;

import org.sopt.assignment.domain.Member;
import org.sopt.assignment.dto.request.CreateMemberRequestDto;
import org.sopt.assignment.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public Long createMember(@RequestBody CreateMemberRequestDto request) {
        return memberService.join(request);
    }

    @GetMapping("/members/{memberId}")
    public Member findMemberById(@PathVariable Long memberId) {
        return memberService.findOne(memberId);
    }

    @GetMapping("/members/all")
    public List<Member> getAllMembers() {
        return memberService.findAllMembers();
    }

    @DeleteMapping("/members/{memberId}")
    public String deleteMember(Long memberId){
        return memberService.delete(memberId);
    }
}