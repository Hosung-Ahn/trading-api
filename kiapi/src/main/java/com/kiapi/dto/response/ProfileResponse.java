package com.kiapi.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kiapi.entity.member.Member;
import com.kiapi.security.aes.AESUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class ProfileResponse {
    private String username;
    private String email;
    private String appKey;
    private String secretKey;
    private String account;

    @JsonIgnore
    private final AESUtil AESUtil;

    @JsonIgnore
    public AESUtil getAESUtil() {
        return AESUtil;
    }

    public ProfileResponse(Member member, AESUtil AESUtil) {
        this.AESUtil = AESUtil;
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.appKey = AESUtil.decrypt(member.getAppKey());
        this.secretKey = AESUtil.decrypt(member.getSecretKey());
        this.account = AESUtil.decrypt(member.getAccount());
    }
}
