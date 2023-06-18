package com.chamal.advice;

import com.chamal.service.exception.DuplicateRecordException;
import com.chamal.service.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUsernameException(DuplicateRecordException e) {
        LocalDateTime timestamp = LocalDateTime.now();
        logger.error("Duplicate username exception occurred: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(timestamp, HttpStatus.CONFLICT.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        LocalDateTime timestamp = LocalDateTime.now();
        logger.error("Not Found exception occurred: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(timestamp, HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
