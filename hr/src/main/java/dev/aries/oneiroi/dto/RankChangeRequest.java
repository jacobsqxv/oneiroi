package dev.aries.oneiroi.dto;

import java.util.List;

public record RankChangeRequest(
		List<RankChange> rankChanges
) {
	public record RankChange(
			Integer employeeId,
			String rank
	) {}
}
