package dev.aries.oneiroi.handler;

import java.time.LocalDateTime;
import java.util.Set;

public record ExceptionResponse(LocalDateTime timestamp, Set<String> cause) {
}
