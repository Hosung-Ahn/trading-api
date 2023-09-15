package com.kiapi.controller.global;

import com.kiapi.dto.auth.LoginSuccess;
import com.kiapi.dto.request.LoginRequest;
import com.kiapi.dto.request.SignupRequest;
import com.kiapi.dto.response.LoginResponse;
import com.kiapi.dto.response.MessageResponse;
import com.kiapi.factory.member.MemberFactory;
import com.kiapi.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private static final String BEARER_PREFIX = "Bearer ";
    private final MemberFactory memberFactory;
    private final AuthService authService;

    @PostMapping("signup")
    public ResponseEntity<?> registerMember(@Valid @RequestBody SignupRequest signupRequest) {
        memberFactory.createUser(signupRequest);
        return ResponseEntity.ok(
                new MessageResponse("회원가입이 완료되었습니다.")
        );
    }

    @PostMapping("login")
    public ResponseEntity<?> authenticateMember(@Valid @RequestBody LoginRequest loginRequest) {
        LoginSuccess loginSuccess = authService.login(loginRequest);

        String accessToken = loginSuccess.getAtRt().getAccessToken();
        String refreshToken = loginSuccess.getAtRt().getRefreshToken();
        Long refreshTokenExpirationFromNowInSeconds =
                loginSuccess.getAtRt().getRefreshTokenExpirationFromNowInSeconds();

        HttpCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .path("/")
                .maxAge(refreshTokenExpirationFromNowInSeconds)
//                .secure(true)
                .httpOnly(true)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + accessToken)
                .body(new LoginResponse(loginSuccess));
    }
}
