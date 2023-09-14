package com.kiapi.controller.global;

import com.kiapi.dto.request.SignupRequest;
import com.kiapi.dto.response.MessageResponse;
import com.kiapi.factory.member.MemberFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final MemberFactory memberFactory;
    @PostMapping("signup")
    public ResponseEntity<?> registerMember(@Valid @RequestBody SignupRequest signupRequest) {
        memberFactory.createUser(signupRequest);
        return ResponseEntity.ok(
                new MessageResponse("회원가입이 완료되었습니다.")
        );
    }
}
