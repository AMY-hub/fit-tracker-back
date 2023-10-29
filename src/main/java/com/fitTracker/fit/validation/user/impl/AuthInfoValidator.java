package com.fitTracker.fit.validation.user.impl;


import com.fitTracker.fit.validation.user.interfaces.IAuthInfoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthInfoValidator implements IAuthInfoValidator {
    private final PasswordEncoder passwordEncoder;
    @Override
    public void throwIfNotValidPassword(String rawPassword, String encodedPassword) throws BadCredentialsException {
        if(rawPassword.isEmpty()) {
            throw new BadCredentialsException("Не указан пароль");
        }
        boolean isPasswordEquals = passwordEncoder.matches(rawPassword, encodedPassword);
        if(!isPasswordEquals) {
            throw new BadCredentialsException("Неверный пароль");
        }
    }
}
