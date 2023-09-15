package com.kiapi.security.jwt;

import com.kiapi.entity.member.Member;
import com.kiapi.repository.MemberRepository;
import com.kiapi.security.userDetails.UserDetailsImpl;
import com.kiapi.service.member.MemberService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class LocalAuthenticationProvider {
    private final MemberRepository memberRepository;
    private final JwtValidator jwtValidator;
    private final JwtProvider jwtProvider;

    private Authentication createAuthentication(String token) {
        jwtValidator.validateToken(token);
        Claims claims = jwtProvider.getClaims(token);

        Collection<? extends GrantedAuthority> authorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(claims.get("authorities").toString());
        Long memberId = claims.get("memberId", Long.class);

        Member member = memberRepository.findByIdWithRoles(memberId)
                .orElseThrow(()->
                        new IllegalArgumentException("존재하지 않는 회원입니다."));
        UserDetails memberDetails = new UserDetailsImpl(member);

        return new UsernamePasswordAuthenticationToken(memberDetails, token, authorities);
    }

    public Authentication createAuthenticationWithAt(String accessToken) {
        jwtValidator.validateAccessToken(accessToken);
        return createAuthentication(accessToken);
    }

    public Authentication createAuthenticationWithRt(String refreshToken) {
        jwtValidator.validateRefreshToken(refreshToken);
        return createAuthentication(refreshToken);
    }
}
