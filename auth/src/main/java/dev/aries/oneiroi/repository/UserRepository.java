package dev.aries.oneiroi.repository;

import java.util.Optional;
import java.util.UUID;

import dev.aries.oneiroi.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByUsername(String username);
}
