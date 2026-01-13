package com.franchise.api.infra.adapter.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.franchise.api.domain.exception.InvalidAttributeException;
import com.franchise.api.domain.exception.FranchiseNotFoundException;
import com.franchise.api.domain.exception.ProductNotFoundException;
import com.franchise.api.domain.exception.BranchNotFoundException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
        FranchiseNotFoundException.class, 
        BranchNotFoundException.class, 
        ProductNotFoundException.class
    })
    public ResponseEntity<Map<String, Object>> handleNotFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                    "timestamp", java.time.LocalDateTime.now(),
                    "error", "Resource Not Found",
                    "message", ex.getMessage(),
                    "status", 404
                ));
    }

    @ExceptionHandler(InvalidAttributeException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(InvalidAttributeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                    "timestamp", java.time.LocalDateTime.now(),
                    "error", "Bad Request",
                    "message", ex.getMessage(),
                    "attribute", ex.getAttribute(),
                    "status", 400
                ));
    }
}
