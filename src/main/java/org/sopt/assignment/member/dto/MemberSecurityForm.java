package org.sopt.assignment.member.dto;

import org.sopt.assignment.member.domain.ERole;
import org.sopt.assignment.member.domain.Member;

public interface MemberSecurityForm {
    Long getUserId();
    ERole getRole();
    String getPassword();

    static MemberSecurityForm invoke(Member member){
        return new MemberSecurityForm() {
            @Override
            public Long getUserId() {
                return member.getId();
            }
            @Override
            public ERole getRole() {
                return member.getRole();
            }
            @Override
            public String getPassword() {
                return null;
            }
        };
    }
}
