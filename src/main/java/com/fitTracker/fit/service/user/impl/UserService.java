package com.fitTracker.fit.service.user.impl;

import com.fitTracker.fit.dto.user.*;
import com.fitTracker.fit.mapper.user.UserDetailsMapper;
import com.fitTracker.fit.mapper.user.UserMapper;
import com.fitTracker.fit.model.Enum.UserRole;
import com.fitTracker.fit.model.user.User;
import com.fitTracker.fit.model.user.UserDetails;
import com.fitTracker.fit.repository.user.UserDetailsRepository;
import com.fitTracker.fit.repository.user.UserRepository;
import com.fitTracker.fit.service.user.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final UserDetailsMapper userDetailsMapper;

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public AuthInfoDto changePassword(ChangePasswordDto changePasswordDto) {
        return null;
    }

    @Override
    public void requestPasswordReset(ResetPasswordDto resetPasswordDto) {

    }

    @Override
    public void restorePassword(RestorePasswordDto restorePasswordDto) {

    }

    @Override
    public User create(RegistrationDto userData) {
//        UserDetailsDto detailsData = userData.getDetails();

        UserDetails details = UserDetails.builder()
                .gender(userData.getGender())
                .height(userData.getHeight())
                .dateOfBirth(userData.getDateOfBirth())
                .initialWeight(userData.getInitialWeight())
                .targetWeight(userData.getTargetWeight())
                .build();

        User user = User.builder()
                .createdAt(OffsetDateTime.now())
                .role(UserRole.USER)
                .name(userData.getName())
                .email(userData.getEmail())
                .password(userData.getPassword())
                .details(details)
                .build();


        userDetailsRepository.saveAndFlush(details);
        return userRepository.saveAndFlush(user);
    }
}
