package com.library.library_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BookNotAvailableException.class)
	public ResponseEntity<Map<String, Object>> handleBookNotAvailableException(BookNotAvailableException ex) {
	    Map<String, Object> errorBody = new HashMap<>();
	    errorBody.put("timestamp", LocalDateTime.now());
	    errorBody.put("status", HttpStatus.NOT_FOUND.value());
	    errorBody.put("error", "Book Not Available");
	    errorBody.put("message", ex.getMessage());
	    return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
	}

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorBody.put("error", "Internal Server Error");
        errorBody.put("message", ex.getMessage());
        return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
