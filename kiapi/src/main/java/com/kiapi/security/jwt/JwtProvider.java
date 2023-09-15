package com.kiapi.security.jwt;

import com.kiapi.security.userDetails.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

// JwtTokenProvider 는 secret key 값을 가지고 있는 객체로 이를 필요로 하는 다양한 곳에서 활용됩니다.<br>
// 따라서 순환 의존성을 가지지 않도록 JwtTokenProvider 에는 추가적인 의존성을 가지지 않는 것을 권장합니다.<br>



/**
 * 이 객체는 말  그대로 JWT 를 생성"만" 해줍니다. JWT 관련 서비스는 JWTService 에서 제공합니다.<br>
 */
//JwtTokenProvider 는 secret key 가지고, 이를 필요로 하는 다양한 곳에서 활용됩니다.
//따라서 순환 의존성을 가지지 않도록 JwtTokenProvider 에는 추가적인 의존성을 가지지 않는 것을 권장합니다.
@Component
public class JwtProvider {
    @Value("${secret.secret-key}")
    private String secretKey;
    @Value("${secret.access-token-validity-in-seconds}")
    private Long accessTokenValidityInSeconds;
    @Value("${secret.refresh-token-validity-in-seconds}")
    private Long refreshTokenValidityInSeconds;
    private Key key;

    @PostConstruct
    protected void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String createAccessToken(Authentication authentication) {
        UserDetailsImpl memberDetails = (UserDetailsImpl) authentication.getPrincipal();

        Date validity = getDateAfter(this.accessTokenValidityInSeconds);

        return createToken(memberDetails, validity);
    }

    public String createRefreshToken(Authentication authentication) {
        UserDetailsImpl memberDetails = (UserDetailsImpl) authentication.getPrincipal();

        Date validity = getDateAfter(this.refreshTokenValidityInSeconds);

        return createToken(memberDetails, validity);
    }

    private Date getDateAfter(long seconds) {
        return new Date(new Date().getTime() + seconds * 1000);
    }

    private String createToken(UserDetailsImpl memberDetails, Date validity) {
        return Jwts.builder()
                .setSubject(memberDetails.getUsername())
                .claim("memberId", memberDetails.getId())
                .claim("authorities", memberDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")))
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

}
