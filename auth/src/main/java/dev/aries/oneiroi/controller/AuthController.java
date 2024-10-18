package dev.aries.oneiroi.controller;

import dev.aries.oneiroi.dto.AuthRequest;
import dev.aries.oneiroi.dto.AuthResponse;
import dev.aries.oneiroi.dto.UserResponse;
import dev.aries.oneiroi.service.authservice.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}

	@GetMapping("/validate-token")
	public ResponseEntity<UserResponse> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
		return ResponseEntity.ok(authService.validateToken(token));
	}

}
