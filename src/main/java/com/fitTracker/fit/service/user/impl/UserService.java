package com.fitTracker.fit.service.user.impl;

import com.fitTracker.fit.dto.user.*;
import com.fitTracker.fit.exception.UserNotFoundException;
import com.fitTracker.fit.mapper.user.UserParamsMapper;
import com.fitTracker.fit.model.user.User;
import com.fitTracker.fit.repository.user.UserParamsRepository;
import com.fitTracker.fit.repository.user.UserRepository;
import com.fitTracker.fit.service.user.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserParamsRepository userDetailsRepository;
    private final UserParamsMapper userDetailsMapper;

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("ID", id.toString()));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("email", email));
    }

    @Override
    public AuthResultDto changePassword(ChangePasswordDto changePasswordDto) {
        return null;
    }

    @Override
    public void requestPasswordReset(ResetPasswordDto resetPasswordDto) {

    }

    @Override
    public void restorePassword(RestorePasswordDto restorePasswordDto) {

    }
}
