package com.fitTracker.fit.exception;

import com.fitTracker.fit.model.Enum.ErrorType;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends AbstractRuntimeException {
    public InternalServerException (String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.SYSTEM, message);
    }
}
