package com.fitTracker.fit.validation.user.interfaces;

import com.fitTracker.fit.exception.BadRequestException;
import com.fitTracker.fit.exception.NotFoundException;

public interface UserChecker {
    public void throwIfExistsByEmail(String email) throws BadRequestException;

    public void checkIfExistsByEmail(String email) throws NotFoundException;

    public void checkIfExistsById(Long id) throws NotFoundException;
}
