package org.sopt.assignment.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.assignment.auth.domain.RefreshToken;
import org.sopt.assignment.auth.dto.command.LoginCommandDto;
import org.sopt.assignment.auth.dto.request.JoinCommandDto;
import org.sopt.assignment.auth.repository.RefreshTokenRepository;
import org.sopt.assignment.global.dto.JwtDto;
import org.sopt.assignment.global.exception.BaseException;
import org.sopt.assignment.global.security.util.JwtUtil;
import org.sopt.assignment.member.domain.Member;
import org.sopt.assignment.member.exception.MemberErrorCode;
import org.sopt.assignment.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void join(JoinCommandDto command) {

        log.info("회원가입 프로세스 시작 - email: {}", maskEmail(command.email()));

        if(existsMemberByEmail(command.email())){
            log.warn("중복된 이메일 입니다: {}", maskEmail(command.email()));
            throw BaseException.type(MemberErrorCode.NOT_DUPLICATED_EMAIL);
        }

        Member.validateCreation(command.name(), command.email(), command.birthday(), command.gender());

        Member member = Member.create(
                command.name(),
                command.email(),
                command.birthday(),
                command.gender(),
                passwordEncoder.encode(command.password()));

        memberRepository.save(member);
        log.info("회원 가입 완료 - memberId: {}, email: {}", member.getId(), maskEmail(command.email()));

    }

    public JwtDto login(LoginCommandDto command) {

        log.info("로그인 시작");
        Member member = memberRepository.findByEmail(command.email())
                .orElseThrow(() -> BaseException.type(MemberErrorCode.NOT_FOUND_MEMBER));

        matchPassword(command.password(), member.getPassword());

        JwtDto jwtDto = jwtUtil.generateTokens(member.getId(), member.getRole());

        refreshTokenRepository.save(RefreshToken.issueRefreshToken(member.getId(), jwtDto.refreshToken()));


        return jwtDto;

    }

    public void logout(Long userId){
        refreshTokenRepository.deleteById(userId.toString());
    }

    private void matchPassword(String password, String userPassword) {
        if(!passwordEncoder.matches(password, userPassword)){
            throw BaseException.type(MemberErrorCode.INVALID_PASSWORD);
        }

    }

    private boolean existsMemberByEmail(String email) {
        return memberRepository.existsMemberByEmail(email);
    }

    private String maskEmail(String email) {
        if (email == null || email.length() < 3) return "***";
        int atIndex = email.indexOf('@');
        if (atIndex <= 0) return "***";

        return email.charAt(0) + "***" + email.substring(atIndex);
    }
}
