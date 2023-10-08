package com.fitTracker.fit.model.Enum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserRole {
    USER("user"),
    ADMIN("admin");

    private final String value;
}
