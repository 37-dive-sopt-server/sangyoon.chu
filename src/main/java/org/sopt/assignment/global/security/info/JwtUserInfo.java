package org.sopt.assignment.global.security.info;

import io.jsonwebtoken.Claims;
import org.sopt.assignment.global.constants.Constants;
import org.sopt.assignment.member.domain.ERole;

public record JwtUserInfo(Long userId, ERole role) {

    public static JwtUserInfo from(Claims claims) {
        return new JwtUserInfo(
                claims.get(Constants.CLAIM_USER_ID, Long.class),
                ERole.valueOf(claims.get(Constants.CLAIM_USER_ROLE, String.class))
        );
    }
}
