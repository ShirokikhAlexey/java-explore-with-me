package ru.prakticum.ewm.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    private final List<String> errors;
    private final String message;
    private final String reason;
    private final String status;
    private final LocalDateTime timestamp;

    public ErrorResponse(String error) {
        this.message = error;
        this.errors = new ArrayList<>();
        this.reason = null;
        this.status = null;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String message, String reason, String status, LocalDateTime timestamp) {
        this.message = message;
        this.errors = new ArrayList<>();
        this.reason = reason;
        this.status = status;
        this.timestamp = timestamp;
    }

    public ErrorResponse getError() {
        return this;
    }
}
