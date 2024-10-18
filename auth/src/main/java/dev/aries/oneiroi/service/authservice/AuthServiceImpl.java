package dev.aries.oneiroi.service.authservice;

import java.util.UUID;

import dev.aries.oneiroi.dto.AuthRequest;
import dev.aries.oneiroi.dto.AuthResponse;
import dev.aries.oneiroi.dto.UserResponse;
import dev.aries.oneiroi.model.User;
import dev.aries.oneiroi.repository.UserRepository;
import dev.aries.oneiroi.security.TokenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final UserRepository userRepo;
	private final TokenService tokenService;
	private final AuthenticationManager authManager;

	@Override
	public AuthResponse login(AuthRequest request) {
		User user = checkUser(request.username());
		Authentication auth = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(
						request.username(), request.password()
				));
		log.info("Generating token for {}", request.username());
		String token = tokenService.generateToken(auth);
		log.info("Generated token: {}", token);
		return AuthResponse.response(user,token);
	}

	@Override
	public UserResponse validateToken(String token) {
		UUID userId = tokenService.validateJwt(token);
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
		return UserResponse.basicResponse(user);
	}

	private User checkUser(String username) {
		return userRepo.findByUsername(username)
				.orElseThrow(() -> new EntityNotFoundException("Username invalid"));
	}
}
