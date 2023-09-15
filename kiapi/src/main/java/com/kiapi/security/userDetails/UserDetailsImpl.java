package com.kiapi.security.userDetails;

import com.kiapi.entity.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * UserDetialsImpl은 Member 의 roles 프로퍼티를 반드시 필요로 합니다.<br>
 * roles 는 Lazy Loading 으로 설정되어 있기 때문에 이를 주의해주세요.
 */
public class UserDetailsImpl implements UserDetails {

    private Member member;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Member member) {
        this.member = member;
        this.authorities =  member.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

    public Member getMember() {
        return member;
    }

    public Long getId() {
        return member.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
