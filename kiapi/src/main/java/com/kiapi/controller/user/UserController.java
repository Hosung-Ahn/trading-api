package com.kiapi.controller.user;

import com.kiapi.dto.response.ProfileResponse;
import com.kiapi.entity.member.Member;
import com.kiapi.security.aes.AESUtil;
import com.kiapi.security.userDetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final AESUtil AESUtil;
    @GetMapping("profile")
    public ResponseEntity<?> registerMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        Member member = principal.getMember();
        return ResponseEntity.ok(
                new ProfileResponse(member, AESUtil)
        );
    }
}
