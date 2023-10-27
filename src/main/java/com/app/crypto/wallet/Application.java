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

}
