package com.fitTracker.fit.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractRuntimeException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;
    public AbstractRuntimeException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
