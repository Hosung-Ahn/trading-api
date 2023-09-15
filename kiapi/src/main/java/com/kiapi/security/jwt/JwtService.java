package com.kiapi.security.jwt;

import com.kiapi.dto.jwt.AtRt;
import com.kiapi.service.redis.AccessTokenService;
import com.kiapi.service.redis.RefreshTokenService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtProvider jwtProvider;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationProvider authenticationProvider;

    private AtRt createAtRt(Authentication authentication) {
        String accessToken = jwtProvider.createAccessToken(authentication);
        String refreshToken = jwtProvider.createRefreshToken(authentication);
        Long refreshTokenExpirationInMilliseconds = getTokenExpirationInMilliseconds(refreshToken);

        accessTokenService.mapAtToRt(accessToken, refreshToken);
        refreshTokenService.mapRtToAt(refreshToken, accessToken);
        return new AtRt(accessToken, refreshToken, refreshTokenExpirationInMilliseconds);
    }

    public AtRt refresh(String refreshToken) {
        Authentication authentication =
                authenticationProvider.createAuthenticationWithRt(refreshToken);

        String oldAccessToken = refreshTokenService.getAt(refreshToken);
        accessTokenService.deleteAt(oldAccessToken);
        refreshTokenService.deleteRt(refreshToken);

        return createAtRt(authentication);
    }

    private Long getTokenExpirationInMilliseconds(String token) {
        Claims claims = jwtProvider.getClaims(token);
        return claims.getExpiration().getTime();
    }
}
