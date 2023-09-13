package com.kiapi;

import com.kiapi.entity.member.ERole;
import com.kiapi.entity.member.Role;
import com.kiapi.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KiapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KiapiApplication.class, args);

	}

	@Bean
	public CommandLineRunner initRoles(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName(ERole.ROLE_USER) == null) {
				roleRepository.save(new Role(ERole.ROLE_USER));
			}
			if (roleRepository.findByName(ERole.ROLE_MODERATOR) == null) {
				roleRepository.save(new Role(ERole.ROLE_MODERATOR));
			}
			if (roleRepository.findByName(ERole.ROLE_ADMIN) == null) {
				roleRepository.save(new Role(ERole.ROLE_ADMIN));
			}
		};
	}

}
