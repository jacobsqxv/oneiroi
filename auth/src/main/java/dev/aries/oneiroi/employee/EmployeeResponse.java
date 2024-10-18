package dev.aries.oneiroi.employee;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EmployeeResponse(
		Integer id,
		String fullName,
		String email,
		String phoneNumber,
		DepartmentResponse department,
		String rank,
		String status,
		LocalDateTime hireDate
) {
}
