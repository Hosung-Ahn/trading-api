package com.kiapi.entity.member;

import com.kiapi.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @Column(unique = true)
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;

    @NotBlank
    private String appKey;
    @NotBlank
    private String secretKey;
    @NotBlank
    private String account;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "member_roles",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Builder

    public Member(String username, String email, String password, String appKey, String secretKey, String account, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.appKey = appKey;
        this.secretKey = secretKey;
        this.account = account;
        this.roles = roles;
    }
}
