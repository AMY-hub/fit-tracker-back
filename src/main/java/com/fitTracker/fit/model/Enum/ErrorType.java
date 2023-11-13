package com.fitTracker.fit.model.Enum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorType {
    VALIDATION("validation"),
    SERVICE("service"),
    SYSTEM("system");

    private final String value;
}
