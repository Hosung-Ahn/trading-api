package com.kiapi.repository;

import com.kiapi.entity.member.ERole;
import com.kiapi.entity.member.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}
