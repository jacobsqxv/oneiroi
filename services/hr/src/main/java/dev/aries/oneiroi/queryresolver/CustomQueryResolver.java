package dev.aries.oneiroi.queryresolver;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

@Component
public class CustomQueryResolver {

	public OffsetDateTime convertLocalDateTime(LocalDateTime localDT) {
		return OffsetDateTime.of(localDT, ZoneOffset.UTC);
	}


}
