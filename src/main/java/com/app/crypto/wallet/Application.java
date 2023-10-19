package com.app.crypto.wallet;

import com.app.crypto.wallet.domain.Role;
import com.app.crypto.wallet.domain.User;
import com.app.crypto.wallet.repository.RoleRepository;
import com.app.crypto.wallet.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository userRepository, RoleRepository roleRepository) {
		return args -> {
			List<Role> roles = new ArrayList<>();
			roles.add(new Role(1L,"ADMIN"));
			roles.add(new Role(2L, "USER"));
			roleRepository.saveAll(roles);
			User admin = new User(1L, "admin", "admin123", "admin@123", true, Collections.singletonList(roles.get(0)));
			userRepository.save(admin);
			User user = new User(2L,"jan", "jan123", "user@123", true, Collections.singletonList(roles.get(1)));
			userRepository.save(user);
		};

	}

}
