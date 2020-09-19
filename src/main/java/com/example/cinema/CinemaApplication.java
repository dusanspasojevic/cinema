package com.example.cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.cinema.models.User;
import com.example.cinema.repositories.UserRepository;

@SpringBootApplication
public class CinemaApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) {
		User admin = new User();
		admin.setUsername("dule");
		admin.setPassword("dule");
		admin.setRole("ADMIN");
		admin.setActive(true);

		this.userRepository.save(admin);
	}

	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}

}
