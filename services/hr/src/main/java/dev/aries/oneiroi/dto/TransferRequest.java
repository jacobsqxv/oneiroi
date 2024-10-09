package dev.aries.oneiroi.dto;

import java.util.List;

public record TransferRequest(
		List<Transfer> transfers
) {
	public record Transfer(
			Integer employeeId,
			Integer departmentId
	) {
	}
}
