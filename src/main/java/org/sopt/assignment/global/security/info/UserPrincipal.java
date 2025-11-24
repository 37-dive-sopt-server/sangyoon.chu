package org.sopt.assignment.global.security.info;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.assignment.member.domain.ERole;
import org.sopt.assignment.member.dto.MemberSecurityForm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Builder
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {
    private final Long userId;
    private final String password;
    private final ERole role;
    private final Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(MemberSecurityForm securityForm) {
        return UserPrincipal.builder()
                .userId(securityForm.getUserId())
                .password(securityForm.getPassword())
                .role(securityForm.getRole())
                .authorities(
                        Collections.singleton(
                                new SimpleGrantedAuthority(securityForm.getRole().getSecurityRole())
                        )
                )
                .build();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public String getUsername() {
        return this.password;
    }
}
