package com.kiapi.dto.auth;

import com.kiapi.dto.jwt.AtRt;
import com.kiapi.entity.member.Member;
import lombok.Data;

@Data
public class LoginSuccess {
    private Long memberId;
    private String username;
    private String email;
    private AtRt atRt;

    public LoginSuccess(Member member, AtRt atRt) {
        this.memberId = member.getId();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.atRt = atRt;
    }
}
