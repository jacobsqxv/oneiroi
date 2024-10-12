package dev.aries.oneiroi.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Column(unique = true, nullable = false)
	private String email;
	@Column(unique = true, nullable = false)
	private String phoneNumber;
	@ManyToOne(cascade = CascadeType.ALL)
	@JsonBackReference
	private Department department;
	@Enumerated(EnumType.STRING)
	private Rank rank;
	@Enumerated(EnumType.STRING)
	private EmployeeStatus status;
	@CreatedDate
	@Column(updatable = false, nullable = false)
	private LocalDateTime createdAt;

	public Employee(String firstName, String lastName, String email, String phoneNumber, Department department, Rank rank) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.department = department;
		this.rank = rank;
		this.status = EmployeeStatus.PROBATION;
	}

	public String fullName() {
		return String.format("%s %s", this.firstName, this.lastName);
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", rank=" + rank.toString() +
				", status=" + status.toString() +
				", createdAt=" + createdAt +
				'}';
	}
}

