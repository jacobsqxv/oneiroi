package dev.aries.oneiroi.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.aries.oneiroi.employee.EmployeeResponse;
import dev.aries.oneiroi.model.User;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(
		UUID id,
		String username,
		String role,
		String status,
		EmployeeResponse employee,
		LocalDateTime createdAt
) {
	public static UserResponse fullResponse(User user, EmployeeResponse employee) {
		return new UserResponse(
				user.getId(),
				user.getUsername(),
				user.getRole().name(),
				user.getStatus().name(),
				employee,
				user.getCreatedAt()
		);
	}

	public static UserResponse listResponse(User user) {
		return UserResponse.builder()
				.id(user.getId())
				.username(user.getUsername())
				.role(user.getRole().name())
				.build();
	}

	public static UserResponse basicResponse(User user) {
		return UserResponse.builder()
				.id(user.getId())
				.username(user.getUsername())
				.role(user.getRole().name())
				.status(user.getStatus().name()).build();
	}
}
