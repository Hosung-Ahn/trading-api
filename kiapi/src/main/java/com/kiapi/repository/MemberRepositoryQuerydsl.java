package com.kiapi.repository;

import com.kiapi.entity.member.Member;

import java.util.Optional;

public interface MemberRepositoryQuerydsl {
    Optional<Member> findByEmailWithRoles(String email);
    Optional<Member> findByIdWithRoles(Long id);
}
