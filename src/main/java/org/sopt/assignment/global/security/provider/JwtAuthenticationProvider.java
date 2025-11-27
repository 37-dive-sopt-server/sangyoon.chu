package org.sopt.assignment.global.security.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.assignment.global.security.info.JwtAuthenticationToken;
import org.sopt.assignment.global.security.info.JwtUserInfo;
import org.sopt.assignment.global.security.info.UserPrincipal;
import org.sopt.assignment.global.security.service.CustomUserDetailService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailService customUserDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("Authenticated User Process");
            return authOfAfterLogin((JwtUserInfo) authentication.getPrincipal());

    }

    private Authentication authOfAfterLogin(JwtUserInfo jwtUserInfo) {
        UserPrincipal userPrincipal = customUserDetailService.loadUserById(jwtUserInfo.userId());
        return new JwtAuthenticationToken(userPrincipal);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
