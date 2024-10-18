package dev.aries.oneiroi.controller;

import java.util.List;
import java.util.UUID;

import dev.aries.oneiroi.dto.PasswordRequest;
import dev.aries.oneiroi.dto.UserRequest;
import dev.aries.oneiroi.dto.UserResponse;
import dev.aries.oneiroi.service.userservice.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping
	@PreAuthorize("hasAnyAuthority('SCOPE_SUPER_ADMIN','SCOPE_ADMIN')")
	public ResponseEntity<UserResponse> addNewUser(@RequestBody UserRequest request) {
		return new ResponseEntity<>(userService.addNewUser(request), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}

	@PutMapping("/{id}/change-password")
	public ResponseEntity<String> changePassword(@PathVariable UUID id, @RequestBody PasswordRequest request) {
		return ResponseEntity.ok(userService.changePassword(id, request));
	}

	@PutMapping("/{id}/reset-password")
	public ResponseEntity<String> resetPassword(@PathVariable UUID id, @RequestBody PasswordRequest request) {
		return ResponseEntity.ok(userService.resetPassword(id, request));
	}
}
