package dev.aries.oneiroi.service.authservice;

import dev.aries.oneiroi.dto.AuthRequest;
import dev.aries.oneiroi.dto.AuthResponse;
import dev.aries.oneiroi.dto.UserResponse;

public interface AuthService {
	AuthResponse login(AuthRequest request);

	UserResponse validateToken(String token);
}
