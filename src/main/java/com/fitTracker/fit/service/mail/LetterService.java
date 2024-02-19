package com.fitTracker.fit.service.mail;

public interface LetterService {
    void sendPasswordResetLetter(String token, String email);
}
