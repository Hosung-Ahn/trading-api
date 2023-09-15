package com.kiapi.dto.jwt;

import lombok.Data;

import java.util.Date;

@Data
public class AtRt {
    String accessToken;
    String refreshToken;
    Long refreshTokenExpirationInMilliseconds;


    public AtRt(String accessToken, String refreshToken, Long refreshTokenExpirationInMilliseconds) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.refreshTokenExpirationInMilliseconds = refreshTokenExpirationInMilliseconds;
    }
}
