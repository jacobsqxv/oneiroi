package dev.aries.oneiroi.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "_user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@Column(unique = true, nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false, unique = true)
	private Integer employeeId;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	@Column(nullable = false)
	private Boolean accountEnabled;

	public User(String username, String password, Role role, Integer employeeId) {
		this.username = username;
		this.password = password;
		this.employeeId = employeeId;
		this.role = role;
		this.status = Status.ACTIVE;
		this.accountEnabled = true;
	}

	public User(String username, String password, Integer employeeId) {
		this.username = username;
		this.password = password;
		this.employeeId = employeeId;
		this.role = Role.SUPER_ADMIN;
		this.status = Status.ACTIVE;
		this.accountEnabled = true;
	}
}
