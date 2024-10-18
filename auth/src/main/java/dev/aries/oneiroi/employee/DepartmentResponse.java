package dev.aries.oneiroi.employee;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

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
}
