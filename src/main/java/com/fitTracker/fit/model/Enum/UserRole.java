package com.fitTracker.fit.model.Enum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    public String getRoleWithPrefix() {
        return  "ROLE_" + this.value;
    }
}
