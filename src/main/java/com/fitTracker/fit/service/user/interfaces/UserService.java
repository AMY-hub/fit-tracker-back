package com.fitTracker.fit.service.user.interfaces;

import com.fitTracker.fit.dto.requestDto.user.ChangePasswordDto;
import com.fitTracker.fit.dto.requestDto.user.RequestResetPasswordDto;
import com.fitTracker.fit.dto.requestDto.user.RestorePasswordDto;
import com.fitTracker.fit.dto.responseDto.user.AuthResultDto;
import com.fitTracker.fit.model.user.User;

import java.util.List;

public interface UserService {
    User getById(Long id);

    void deleteById(Long id);

    List<User> findAll();

    User getByEmail(String email);

    AuthResultDto changePassword(ChangePasswordDto changePasswordDto);

    void requestPasswordReset(RequestResetPasswordDto resetPasswordDto);

    void resetPassword(RestorePasswordDto restorePasswordDto);
}