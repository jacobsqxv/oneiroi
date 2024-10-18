package dev.aries.oneiroi.dto;

public record UserRequest(
		String username,
		String password,
		String role,
		Integer employeeId
) {
}
