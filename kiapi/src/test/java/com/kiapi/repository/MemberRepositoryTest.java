package com.kiapi.repository;

import com.kiapi.dto.request.SignupRequest;
import com.kiapi.entity.member.Member;
import com.kiapi.factory.member.MemberFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberFactory memberFactory;
    @Autowired
    EntityManager em;
    @Test
    void existsByUsernameTest() {
        SignupRequest signupRequest = new SignupRequest("test name", "test@com", "test password");
        Member member = memberFactory.createUser(signupRequest);

        em.flush();
        em.clear();
        assertTrue(memberRepository.existsByUsername("test name"));
    }
}