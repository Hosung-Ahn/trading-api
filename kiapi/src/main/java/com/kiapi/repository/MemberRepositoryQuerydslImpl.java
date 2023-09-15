package com.kiapi.repository;

import com.kiapi.entity.member.Member;
import com.kiapi.entity.member.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.kiapi.entity.member.QMember.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberRepositoryQuerydslImpl implements MemberRepositoryQuerydsl{
    private final JPAQueryFactory query;
    @Override
    public Optional<Member> findByEmailWithRoles(String email) {
        return Optional.ofNullable(
                query
                        .selectFrom(member)
                        .join(member.roles)
                        .fetchJoin()
                        .where(member.email.eq(email))
                        .fetchFirst()
        );
    }

    @Override
    public Optional<Member> findByIdWithRoles(Long id) {
        return Optional.ofNullable(
                query
                        .selectFrom(member)
                        .join(member.roles)
                        .fetchJoin()
                        .where(member.id.eq(id))
                        .fetchFirst()
        );
    }
}
