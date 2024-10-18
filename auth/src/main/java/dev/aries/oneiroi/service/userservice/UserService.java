package dev.aries.oneiroi.service.userservice;

import java.util.List;
import java.util.UUID;

import dev.aries.oneiroi.dto.PasswordRequest;
import dev.aries.oneiroi.dto.UserRequest;
import dev.aries.oneiroi.dto.UserResponse;

public interface UserService {
	UserResponse addNewUser(UserRequest request);

	List<UserResponse> getAllUsers();

	UserResponse getUserById(UUID id);

	String changePassword(UUID id, PasswordRequest request);

	String resetPassword(UUID id, PasswordRequest request);
}
