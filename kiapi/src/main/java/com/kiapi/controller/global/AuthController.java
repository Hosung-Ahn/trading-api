package com.kiapi.controller.global;

import com.kiapi.dto.request.LoginRequest;
import com.kiapi.dto.request.SignupRequest;
import com.kiapi.dto.response.MessageResponse;
import com.kiapi.factory.member.MemberFactory;
import com.kiapi.security.userDetails.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final MemberFactory memberFactory;
    private final AuthenticationManager authenticationManager;
    @PostMapping("signup")
    public ResponseEntity<?> registerMember(@Valid @RequestBody SignupRequest signupRequest) {
        memberFactory.createUser(signupRequest);
        return ResponseEntity.ok(
                new MessageResponse("회원가입이 완료되었습니다.")
        );
    }

    @PostMapping("login")
    public ResponseEntity<?> authenticateMember(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserDetails principal = (UserDetailsImpl) authenticate.getPrincipal();


        return ResponseEntity.ok(
                new MessageResponse("로그인이 완료되었습니다.")
        );
    }
}
