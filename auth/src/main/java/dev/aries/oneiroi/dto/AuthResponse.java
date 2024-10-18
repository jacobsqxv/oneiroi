package dev.aries.oneiroi.dto;

import dev.aries.oneiroi.model.User;

public record AuthResponse(
		String username,
		String accessToken,
		String role,
		String status
) {

	public static AuthResponse response(User user, String token) {
		return new AuthResponse(
				user.getUsername(),
				token,
				user.getRole().name(),
				user.getStatus().name()
		);
	}
}
