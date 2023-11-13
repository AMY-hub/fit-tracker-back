package com.fitTracker.fit.validation.user.impl;

import com.fitTracker.fit.exception.BadRequestException;
import com.fitTracker.fit.exception.NotFoundException;
import com.fitTracker.fit.model.user.User;
import com.fitTracker.fit.repository.user.UserRepository;
import com.fitTracker.fit.validation.user.interfaces.UserChecker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.fitTracker.fit.util.ErrorTemplates.USER_EXISTS;
import static com.fitTracker.fit.util.ErrorTemplates.USER_NOT_FOUND;

@Component
@AllArgsConstructor
public class UserCheckerImpl implements UserChecker {
    private final UserRepository userRepository;
    @Override
    public void throwIfExistsByEmail(String email) throws BadRequestException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()) {
            throw new BadRequestException(USER_EXISTS, "email", email);
        }
    }

    @Override
    public void checkIfExistsByEmail(String email) throws NotFoundException {
        userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND, "email", email));
    }

    @Override
    public void checkIfExistsById(Long id) throws NotFoundException {
        userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND, "ID", id.toString()));
    }
}
