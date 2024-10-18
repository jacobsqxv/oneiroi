package dev.aries.oneiroi.handler;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Data;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class ResponseHandler {
	private HttpStatus status;
	private Object data;

	public static ResponseEntity<Object> successResponse(HttpStatus status, Object data) {
		return response("success", status, data);
	}

	public static ResponseEntity<Object> errorResponse(HttpStatus status, Object data) {
		return response("error", status, data);
	}

	public static ResponseEntity<Object> response(String statusMessage, HttpStatus status, Object data) {
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("status", statusMessage);
		response.put("content", data);
		return new ResponseEntity<>(response, status);
	}

}
