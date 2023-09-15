package com.kiapi.service.auth;

import com.kiapi.dto.auth.LoginSuccess;
import com.kiapi.dto.jwt.AtRt;
import com.kiapi.dto.request.LoginRequest;
import com.kiapi.entity.member.Member;
import com.kiapi.security.jwt.JwtService;
import com.kiapi.security.userDetails.UserDetailsImpl;
import com.kiapi.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public LoginSuccess login(LoginRequest loginRequest) {
        // 여기서 생성된 authentication 안에 들어 있는 member 는 실제 DB 에서 가져온 member 입니다.
        // 관련해서는 UserDetailsServiceImpl 를 참고해주세요.
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
        Member member = userDetails.getMember();
        AtRt atRt = jwtService.createAtRt(authenticate);

        return new LoginSuccess(member, atRt);
    }

}
