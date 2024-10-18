package dev.aries.oneiroi.data;

import dev.aries.oneiroi.dto.UserRequest;
import dev.aries.oneiroi.dto.UserResponse;
import dev.aries.oneiroi.model.User;
import dev.aries.oneiroi.repository.UserRepository;
import dev.aries.oneiroi.service.userservice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Loader implements CommandLineRunner {
	private final UserRepository userRepo;
	private final UserService userService;
	private final PasswordEncoder encoder;

	@Override
	public void run(String... args) throws Exception {
		loadUser();
	}

	private void loadUser() {
		if (userRepo.count() == 0) {
			UserRequest request = new UserRequest(
					"super.admin",
					"Admin123",
					"SUPER_ADMIN",
					1
			);
			UserResponse response = userService.addNewUser(request);
			log.info("User loaded successfully: {}", response.username());
		}
	}
}
