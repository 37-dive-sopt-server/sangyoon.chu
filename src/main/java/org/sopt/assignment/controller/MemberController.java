package org.sopt.assignment.controller;

import org.sopt.assignment.domain.Member;
import org.sopt.assignment.service.MemberServiceImpl;

import java.util.List;
import java.util.Optional;

public class MemberController {

    private final MemberServiceImpl memberService = new MemberServiceImpl();

    public Long createMember(String name) {

        return memberService.join(name);
    }

    public Optional<Member> findMemberById(Long id) {
        return memberService.findOne(id);
    }

    public List<Member> getAllMembers() {
        return memberService.findAllMembers();
    }
}