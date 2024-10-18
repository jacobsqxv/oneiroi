package dev.aries.oneiroi.dto;

public record DepartmentRequest(
		String name,
		String description,
		Double budget
) {

	public DepartmentRequest addDept(DepartmentRequest request) {
		if (request.name() == null || request.budget() == null) {
			throw new NullPointerException("Name and Budget are required");
		}
		return request;
	}
}
