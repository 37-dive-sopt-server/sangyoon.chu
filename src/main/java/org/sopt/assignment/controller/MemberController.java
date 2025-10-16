package org.sopt.assignment.controller;

import org.sopt.assignment.domain.Gender;
import org.sopt.assignment.domain.Member;
import org.sopt.assignment.service.MemberServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MemberController {

    private final MemberServiceImpl memberService = new MemberServiceImpl();

    public Long createMember(String name, String email, LocalDate birthday, Gender gender) {
        return memberService.join(name, email, birthday, gender);
    }

    public Optional<Member> findMemberById(Long id) {
        return memberService.findOne(id);
    }

    public List<Member> getAllMembers() {
        return memberService.findAllMembers();
    }

    public boolean existsMemberByEmail(String email) {
        return memberService.existsMemberByEmail(email);
    }
}