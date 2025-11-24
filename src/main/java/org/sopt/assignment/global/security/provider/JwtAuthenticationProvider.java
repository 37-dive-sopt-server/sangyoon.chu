package org.sopt.assignment.global.security.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.assignment.global.security.info.JwtUserInfo;
import org.sopt.assignment.global.security.info.UserPrincipal;
import org.sopt.assignment.global.security.service.CustomUserDetailService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomUserDetailService customUserDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("AuthenticationProvider Initiate");
        if(authentication.getPrincipal().getClass().equals(String.class)) {
            log.info("Non-Authenticated User Process");
            return authOfLogin(authentication);
        }else {
            log.info("Authenticated User Process");
            return authOfAfterLogin((JwtUserInfo) authentication.getPrincipal());
        }
    }

    private Authentication authOfLogin(Authentication authentication) {
        UserPrincipal userPrincipal = customUserDetailService.loadUserByUsername(authentication.getPrincipal().toString());

        if(!bCryptPasswordEncoder
                .matches(authentication.getCredentials().toString(), userPrincipal.getPassword()))
            throw new UsernameNotFoundException("비밀번호가 일치하지 않습니다!");

        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }

    private Authentication authOfAfterLogin(JwtUserInfo jwtUserInfo) {
        UserPrincipal userPrincipal = customUserDetailService.loadUserById(jwtUserInfo.userId());
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
