package com.kiapi.repository;

import com.kiapi.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<Member> findByEmail(String email);
}
