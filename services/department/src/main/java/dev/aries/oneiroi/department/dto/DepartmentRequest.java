package dev.aries.oneiroi.department.dto;

public record DepartmentRequest(
		String name,
		String description,
		Double budget
) {
}
