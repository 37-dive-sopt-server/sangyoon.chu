package org.sopt.assignment.global.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import org.sopt.assignment.global.constants.Constants;
import org.sopt.assignment.global.dto.JwtDto;
import org.sopt.assignment.member.domain.ERole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret")
    private String secret;

    @Value("${jwt.access-token.expiration}")
    @Getter
    private Integer accessExpiration;

    @Value("${jwt.refresh-token.expiration}")
    @Getter
    private Integer refreshExpiration;

    private Key key;

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(Long id, ERole role, Integer expiration) {
        Claims claims = Jwts.claims();
        claims.put(Constants.CLAIM_USER_ID, id.toString());
        if (role != null)
            claims.put(Constants.CLAIM_USER_ROLE, role);

        return Jwts.builder()
                .setHeaderParam(Header.JWT_TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public JwtDto generateTokens(Long id, ERole role) {
        return JwtDto.of(
                generateToken(id, role, accessExpiration),
                generateToken(id, role, refreshExpiration)
        );
    }
}
