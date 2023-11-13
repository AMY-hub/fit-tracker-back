package com.fitTracker.fit.validation.auth.impl;


import com.fitTracker.fit.validation.auth.interfaces.AuthInfoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthInfoValidatorImpl implements AuthInfoValidator {
    private final PasswordEncoder passwordEncoder;
    @Override
    public void throwIfNotValidPassword(String rawPassword, String encodedPassword) throws BadCredentialsException {
        if(rawPassword.isEmpty()) {
            throw new BadCredentialsException("Password could not be empty");
        }
        boolean isPasswordEquals = passwordEncoder.matches(rawPassword, encodedPassword);
        if(!isPasswordEquals) {
            throw new BadCredentialsException("Wrong password");
        }
    }
}
