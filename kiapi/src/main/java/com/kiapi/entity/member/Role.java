package com.kiapi.entity.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "roles")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Role {
    @Id @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }
}
