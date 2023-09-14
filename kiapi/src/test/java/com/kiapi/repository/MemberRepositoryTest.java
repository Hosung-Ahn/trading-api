package com.kiapi.repository;

import com.kiapi.entity.member.Member;
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
    EntityManager em;
    @Test
    void existsByUsernameTest() {
        Member member = new Member("test name", "test@com", "test password");
        memberRepository.save(member);

        em.flush();
        em.clear();
        assertTrue(memberRepository.existsByUsername("test name"));
    }
}