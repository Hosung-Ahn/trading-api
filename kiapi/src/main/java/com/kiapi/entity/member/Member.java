package com.kiapi.entity.member;

import com.kiapi.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;
}
