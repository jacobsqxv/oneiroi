package dev.aries.oneiroi.department.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record DepartmentResponse(
		Integer id,
		String name,
		Set<Integer> employees,
		Double budget,
		LocalDateTime createdAt
) {
}
