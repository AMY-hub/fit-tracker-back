package com.fitTracker.fit.exception;

import com.fitTracker.fit.model.Enum.ErrorType;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends AbstractRuntimeException {
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, ErrorType.SERVICE, message);
    }

    public NotFoundException(String body, String target, String targetValue) {
        super(HttpStatus.NOT_FOUND, ErrorType.SERVICE, body, target, targetValue);
    }
}
