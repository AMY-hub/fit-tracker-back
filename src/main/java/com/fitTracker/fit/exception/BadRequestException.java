package com.fitTracker.fit.exception;

import com.fitTracker.fit.model.Enum.ErrorType;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends AbstractRuntimeException {
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, ErrorType.SERVICE, message);
    }

    public BadRequestException(String body, String target, String targetValue) {
        super(HttpStatus.NOT_FOUND, ErrorType.SERVICE, body, target, targetValue);
    }
}
