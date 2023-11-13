package com.fitTracker.fit.exception;

import com.fitTracker.fit.model.Enum.ErrorType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractRuntimeException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;
    private final ErrorType type;
    public AbstractRuntimeException(HttpStatus httpStatus, ErrorType errorType, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
        this.type = errorType;
    }

    public AbstractRuntimeException(HttpStatus httpStatus, ErrorType errorType, String body, String target, String targetValue) {
        super(body);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(body);
        stringBuilder.append("with");
        stringBuilder.append(target);
        stringBuilder.append(targetValue);

        this.message = stringBuilder.toString();
        this.httpStatus = httpStatus;
        this.type = errorType;
    }
}
