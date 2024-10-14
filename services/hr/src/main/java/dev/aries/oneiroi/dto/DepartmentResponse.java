package dev.aries.oneiroi.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.aries.oneiroi.model.Department;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DepartmentResponse(
		Integer id,
		String name,
		String description,
		Double budget,
		Set<EmployeeResponse> employees,
		String status,
		LocalDateTime createdAt
) {

	public static DepartmentResponse fullResponse(Department dept) {
		return new DepartmentResponse(
				dept.getId(),
				dept.getName(),
				dept.getDescription(),
				dept.getBudget(),
				dept.getEmployees().stream()
						.map(EmployeeResponse::listResponse)
						.collect(Collectors.toSet()),
				dept.getStatus().toString(),
				dept.getCreatedAt());
	}

	public static DepartmentResponse basicResponse(Department dept) {
		return DepartmentResponse.builder()
				.id(dept.getId())
				.name(dept.getName())
				.description(dept.getDescription())
				.budget(dept.getBudget())
				.status(dept.getStatus().toString())
				.build();
	}

	public static DepartmentResponse listResponse(Department dept) {
		return DepartmentResponse.builder()
				.id(dept.getId())
				.name(dept.getName())
				.status(dept.getStatus().toString())
				.build();
	}
}
