package dev.aries.oneiroi.handler;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final LocalDateTime TIMESTAMP = LocalDateTime.now();

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleOtherExceptions(Exception ex) {
		logException(ex);
		Set<String> error = Set.of(ex.getMessage());
		return ResponseHandler.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, new ExceptionResponse(TIMESTAMP, error));
	}

	@ExceptionHandler({ EntityNotFoundException.class })
	public ResponseEntity<Object> handleNotFoundExceptions(Exception ex) {
		logException(ex);
		Set<String> error = Set.of(ex.getMessage());
		return ResponseHandler.errorResponse(HttpStatus.NOT_FOUND, new ExceptionResponse(TIMESTAMP, error));
	}

	private void logException(Exception ex) {
		log.error("Exception Stacktrace: ", ex);
	}
}
