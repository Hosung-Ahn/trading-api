package com.kiapi.dto.jwt;

import lombok.Data;

import java.util.Date;

@Data
public class AtRt {
    String accessToken;
    String refreshToken;
    Long refreshTokenExpirationInMilliseconds;
    Long refreshTokenExpirationFromNowInSeconds;
    private Long calRefreshTokenExpirationFromNowInSeconds(Long refreshTokenExpirationInMilliseconds) {
        return (refreshTokenExpirationInMilliseconds - (new Date().getTime())) / 1000;
    }


    public AtRt(String accessToken, String refreshToken, Long refreshTokenExpirationInMilliseconds) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.refreshTokenExpirationInMilliseconds = refreshTokenExpirationInMilliseconds;
        this.refreshTokenExpirationFromNowInSeconds =
                calRefreshTokenExpirationFromNowInSeconds(refreshTokenExpirationInMilliseconds);
    }
}
