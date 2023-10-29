package com.fitTracker.fit.validation.user.interfaces;

public interface IAuthInfoValidator {
    void throwIfNotValidPassword(String rawPassword, String encodedPassword);
}