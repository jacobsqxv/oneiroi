package dev.aries.oneiroi.dto;

import lombok.Builder;

@Builder
public record EmployeeRequest(
		String firstName,
		String lastName,
		String email,
		String phoneNumber,
		Integer departmentId,
		String rank
) {
	public static EmployeeRequest updateRequest(EmployeeRequest request) {
		return new EmployeeRequest(
				request.firstName(),
				request.lastName(),
				request.email(),
				request.phoneNumber(),
				null,
				null);
	}

	public static EmployeeRequest promotionRequest(EmployeeRequest request) {
		return EmployeeRequest.builder()
				.rank(request.rank())
				.build();
	}
}
