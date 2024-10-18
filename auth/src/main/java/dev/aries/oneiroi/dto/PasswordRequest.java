package dev.aries.oneiroi.dto;

public record PasswordRequest(
		String oldPassword,
		String newPassword
) {
	public static PasswordRequest reset(PasswordRequest request) {
		return new PasswordRequest(null, request.newPassword());
	}
}
