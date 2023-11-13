package com.fitTracker.fit.validation.auth.interfaces;

public interface AuthInfoValidator {
    void throwIfNotValidPassword(String rawPassword, String encodedPassword);
}