package com.sp.ecommerce.shared.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
//@Order(-1)
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<UserErrorResponse> handleBadRequestException(BadRequestException ex) {
        log.error("BadRequestException: {}", ex.getMessage());
        return ResponseEntity
                .status(400)
                .body(createUserErrorResponse(ex, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<UserErrorResponse> handleConflictException(ConflictException ex) {
        log.error("ConflictException: {}", ex.getMessage());
        return ResponseEntity
                .status(409)
                .body(createUserErrorResponse(ex, HttpStatus.CONFLICT));
    }

    // ensure Exception class extends RuntimeException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<UserErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        log.error("ResourceNotFoundException: {}", ex.getMessage());
        return ResponseEntity
                .status(404)
                .body(createUserErrorResponse(ex, HttpStatus.NOT_FOUND, request));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<UserErrorResponse> handleNoSuchElementException(NoSuchElementException ex,
                                                                          WebRequest request) {
        log.error("NoSuchElementException: {}", ex.getMessage());
        return ResponseEntity
                .status(404)
                .body(createUserErrorResponse(ex, HttpStatus.NOT_FOUND, request));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<UserErrorResponse> handleRuntimeException(RuntimeException ex,
                                                                    WebRequest request) {
        log.error("RuntimeException: {}", ex.getMessage());
        return ResponseEntity
                .status(500)
                .body(createUserErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR,
                        request));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<UserErrorResponse> handleInternalServerException(Exception ex,
                                                                           WebRequest request) {
        log.error("Exception: {}", ex.getMessage());
        return ResponseEntity
                .status(500)
                .body(createUserErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request));
    }


    private UserErrorResponse createUserErrorResponse(Exception ex, HttpStatus status) {
        return UserErrorResponse.builder()
                .message(ex.getMessage())
                .status(status.value())
                .timestamp(java.time.Instant.now())
                .build();
    }

    private UserErrorResponse createUserErrorResponse(Exception ex, HttpStatus status, WebRequest request) {
        return UserErrorResponse.builder()
                .message(ex.getMessage())
                .status(status.value())
                .timestamp(java.time.Instant.now())
                .build();
    }
}
