package org.sopt.assignment.controller;

import org.sopt.assignment.domain.Gender;
import org.sopt.assignment.domain.Member;
import org.sopt.assignment.service.MemberService;
import org.sopt.assignment.service.MemberServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/users")
    public Long createMember(String name, String email, LocalDate birthday, Gender gender) {
        return memberService.join(name, email, birthday, gender);
    }

    @GetMapping("/users")
    public Member findMemberById(Long id) {
        return memberService.findOne(id);
    }

    @GetMapping("/users/all")
    public List<Member> getAllMembers() {
        return memberService.findAllMembers();
    }

    public String deleteMember(Long memberId){
        return memberService.delete(memberId);
    }
}