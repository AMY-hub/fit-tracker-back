package com.fitTracker.fit.model.Enum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TokenType {
    REFRESH("refresh"),
    ACCESS("access"),
    RESET("reset");

    private final String value;
}
