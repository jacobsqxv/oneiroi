package dev.aries.oneiroi.dto;

import java.util.List;

public record PromotionRequest(
		List<Promotion> promotions
) {
	public record Promotion(
			Integer employeeId,
			String rank
	) {}
}
