package com.cherepoq.Leaderboard_service.exception;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest req) {
        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now(),
                "message", ex.getMessage()
        );

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
