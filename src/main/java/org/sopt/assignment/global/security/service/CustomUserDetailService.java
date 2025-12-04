package org.sopt.assignment.global.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.assignment.global.exception.BaseException;
import org.sopt.assignment.global.security.info.UserPrincipal;
import org.sopt.assignment.member.dto.MemberSecurityForm;
import org.sopt.assignment.member.exception.MemberErrorCode;
import org.sopt.assignment.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService {

    private final MemberRepository memberRepository;

    public UserPrincipal loadUserById(Long id){
        MemberSecurityForm memberSecurityForm = memberRepository.findMemberSecurityFormById(id)
                .orElseThrow(() -> BaseException.type(MemberErrorCode.NOT_FOUND_MEMBER));

        return UserPrincipal.create(memberSecurityForm);
    }
}
