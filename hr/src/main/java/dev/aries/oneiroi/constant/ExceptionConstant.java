package dev.aries.oneiroi.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionConstant {

	NOT_FOUND("%s not found");

	private final String message;
}
