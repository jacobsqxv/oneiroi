package dev.aries.oneiroi.util;

import java.util.function.Consumer;

public class Util {

	private Util() {
		throw new IllegalStateException();
	}

	public static <T> void updateField(Consumer<T> setter, T currVal, T newVal) {
		if (newVal == null || newVal.equals(currVal)) {
			return;
		}

		setter.accept(newVal);
	}
}
