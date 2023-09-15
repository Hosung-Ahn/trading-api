package com.kiapi.validation;

import com.kiapi.dto.request.SignupRequest;
import com.kiapi.entity.member.Member;
import com.kiapi.factory.member.MemberFactory;
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
    @Autowired
    MemberFactory memberFactory;

    @Test
    void signupValidateTest() {

    }
}