package dev.aries.oneiroi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Rank {

	JUNIOR(2000),
	MID_LEVEL(2500),
	SENIOR(3500),
	EXPERT(5000);

	public final Integer basicSalary;

	@Override
	public String toString() {
		return this.name();
	}
}
