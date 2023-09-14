package com.kiapi.factory.member;

import com.kiapi.dto.request.SignupRequest;
import com.kiapi.entity.member.ERole;
import com.kiapi.entity.member.Member;
import com.kiapi.entity.member.Role;
import com.kiapi.repository.MemberRepository;
import com.kiapi.repository.RoleRepository;
import com.kiapi.validation.AuthValidator;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class MemberFactory {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthValidator authValidator;
    public Member createUser(SignupRequest signupRequest) {
        authValidator.signupValidate(signupRequest);
        Member member = Member.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .roles(Set.of(roleRepository.findByName(ERole.ROLE_USER)))
                .build();
        memberRepository.save(member);
        return member;
    }
}
