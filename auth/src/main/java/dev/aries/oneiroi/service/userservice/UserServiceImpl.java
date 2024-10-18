package dev.aries.oneiroi.service.userservice;

import java.util.List;
import java.util.UUID;

import dev.aries.oneiroi.dto.PasswordRequest;
import dev.aries.oneiroi.dto.UserRequest;
import dev.aries.oneiroi.dto.UserResponse;
import dev.aries.oneiroi.employee.EmployeeClient;
import dev.aries.oneiroi.employee.EmployeeResponse;
import dev.aries.oneiroi.model.Role;
import dev.aries.oneiroi.model.User;
import dev.aries.oneiroi.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final EmployeeClient employeeClient;

	@Override
	public UserResponse addNewUser(UserRequest request) {
		checkUsername(request.username());
		EmployeeResponse employee = getEmployee(request.employeeId());
		User newUser = new User(
				request.username(),
				passwordEncoder.encode(request.password()),
				Role.valueOf(request.role().toUpperCase()),
				employee.id()
		);
		userRepo.save(newUser);
		log.info("New user added: {}", newUser.getUsername());
		return UserResponse.fullResponse(newUser, employee);
	}

	private EmployeeResponse getEmployee(Integer id) {
		EmployeeResponse response = employeeClient.getEmployeeResponse(id);
		if (response == null) {
			throw new IllegalArgumentException("Employee not found or does not exist");
		}
		return response;
	}

	private void checkUsername(String username) {
		if (userRepo.findByUsername(username).isPresent()) {
			throw new EntityExistsException("User already exists");
		}
	}

	@Override
	public List<UserResponse> getAllUsers() {
		return userRepo.findAll().stream()
				.map(UserResponse::listResponse)
				.toList();
	}

	@Override
	public UserResponse getUserById(UUID id) {
		User user = loadUser(id);
		EmployeeResponse employee = employeeClient.getEmployeeResponse(user.getEmployeeId());
		return UserResponse.fullResponse(user, employee);
	}

	@Override
	public String changePassword(UUID id, PasswordRequest request) {
		User user = loadUser(id);
		Boolean passwordMatch = passwordEncoder.matches(request.oldPassword(), user.getPassword());
		if (Boolean.FALSE.equals(passwordMatch)) {
			throw new IllegalArgumentException("Password does not match");
		}
		user.setPassword(passwordEncoder.encode(request.newPassword()));
		userRepo.save(user);
		return "Password changed successfully";
	}

	@Override
	public String resetPassword(UUID id, PasswordRequest request) {
		PasswordRequest resetRequest = PasswordRequest.reset(request);
		User user = loadUser(id);
		user.setPassword(passwordEncoder.encode(resetRequest.newPassword()));
		userRepo.save(user);
		return "Password reset successfully";
	}

	private User loadUser(UUID id) {
		return userRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
	}
}
