package com.fitTracker.fit.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends AbstractRuntimeException {
    private final String messagePattern = "Не найден пользователь с ";
    private final String message;
    public UserNotFoundException(String pattern, String patternValue) {
        super(HttpStatus.NOT_FOUND, "User not found");
        StringBuilder stringBuilder = new StringBuilder(messagePattern)
                .append(pattern)
                .append(' ')
                .append(patternValue);
        this.message = stringBuilder.toString();
    }
}
