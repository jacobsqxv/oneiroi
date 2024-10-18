package dev.aries.oneiroi.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
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
@NamedEntityGraph(name = "Department.employees",
	attributeNodes = @NamedAttributeNode("employees")
)
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique = true, nullable = false)
	private String name;
	private String description;
	@Column(precision = 2, nullable = false)
	private Double budget;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "department")
	@JsonManagedReference
	private Set<Employee> employees = new HashSet<>();
	@Enumerated(EnumType.STRING)
	private DepartmentStatus status;
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	public Department(String name, String description, Double budget) {
		this.name = name;
		this.description = description;
		this.budget = budget;
		this.status = DepartmentStatus.ACTIVE;
	}

	@Override
	public String toString() {
		return "Department{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", budget=" + budget +
				", status=" + status.toString() +
				", createdAt=" + createdAt +
				'}';
	}
}
