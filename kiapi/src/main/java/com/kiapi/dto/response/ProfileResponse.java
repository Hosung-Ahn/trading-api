package com.kiapi.dto.response;

import com.kiapi.entity.member.Member;
import lombok.Data;

@Data
public class ProfileResponse {
    private String username;
    private String email;

    public ProfileResponse(Member member) {
        this.username = member.getUsername();
        this.email = member.getEmail();
    }
}
