package com.fitTracker.fit.service.user.interfaces;

import com.fitTracker.fit.dto.user.*;
import com.fitTracker.fit.model.user.User;

import java.util.List;

public interface IUserService {
    User getById(Long id);

    void deleteById(Long id);

    List<User> findAll();

    User getByEmail(String email);

    AuthResultDto changePassword(ChangePasswordDto changePasswordDto);

    void requestPasswordReset(ResetPasswordDto resetPasswordDto);

    void restorePassword(RestorePasswordDto restorePasswordDto);
}