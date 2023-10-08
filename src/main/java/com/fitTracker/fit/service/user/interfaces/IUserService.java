package com.fitTracker.fit.service.user.interfaces;

import com.fitTracker.fit.dto.user.*;
import com.fitTracker.fit.model.user.User;

public interface IUserService {
    User getById(Long id);

    User getByEmail(String email);

    AuthInfoDto changePassword(ChangePasswordDto changePasswordDto);

    void requestPasswordReset(ResetPasswordDto resetPasswordDto);

    void restorePassword(RestorePasswordDto restorePasswordDto);

    User create(RegistrationDto userData);
}