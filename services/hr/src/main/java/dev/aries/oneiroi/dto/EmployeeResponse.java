package dev.aries.oneiroi.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.aries.oneiroi.model.Employee;
import lombok.Builder;

@Builder
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

	public static EmployeeResponse basicResponse(Employee employee) {
		return EmployeeResponse.builder()
				.id(employee.getId())
				.fullName(employee.fullName())
				.email(employee.getEmail())
				.phoneNumber(employee.getPhoneNumber())
				.rank(employee.getRank().toString())
				.status(employee.getStatus().toString())
				.build();
	}

	public static EmployeeResponse listResponse(Employee employee) {
		return EmployeeResponse.builder()
				.id(employee.getId())
				.fullName(employee.fullName())
				.email(employee.getEmail())
				.rank(employee.getRank().toString())
				.build();
	}

	public static EmployeeResponse fullResponse(Employee employee) {
		return new EmployeeResponse(
				employee.getId(),
				employee.fullName(),
				employee.getEmail(),
				employee.getPhoneNumber(),
				DepartmentResponse.listResponse(employee.getDepartment()),
				employee.getRank().toString(),
				employee.getStatus().toString(),
				employee.getHireDate());
	}

}
