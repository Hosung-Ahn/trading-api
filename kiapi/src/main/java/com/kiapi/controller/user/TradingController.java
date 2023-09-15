package com.kiapi.controller.user;

import com.kiapi.dto.request.SignupRequest;
import com.kiapi.dto.response.MessageResponse;
import com.kiapi.dto.response.ProfileResponse;
import com.kiapi.entity.member.Member;
import com.kiapi.security.userDetails.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class TradingController {
    @GetMapping("profile")
    public ResponseEntity<?> registerMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        Member member = principal.getMember();
        return ResponseEntity.ok(
                new ProfileResponse(member)
        );
    }
}
