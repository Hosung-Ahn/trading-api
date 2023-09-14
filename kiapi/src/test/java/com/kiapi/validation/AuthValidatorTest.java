package com.kiapi.validation;

import com.kiapi.dto.request.SignupRequest;
import com.kiapi.entity.member.Member;
import com.kiapi.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AuthValidatorTest {
    @Autowired
    AuthValidator authValidator;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void signupValidateTest() {
        SignupRequest signupRequest = new SignupRequest("test name", "test@com", "test password");
        authValidator.signupValidate(signupRequest);

        Member member = new Member("test name", "test@com", "test password");
        memberRepository.save(member);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            authValidator.signupValidate(signupRequest);
        });
    }
}