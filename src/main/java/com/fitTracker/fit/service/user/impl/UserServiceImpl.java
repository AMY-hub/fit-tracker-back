package com.fitTracker.fit.service.user.impl;

import com.fitTracker.fit.config.mail.MailConfig;
import com.fitTracker.fit.dto.requestDto.user.ChangePasswordDto;
import com.fitTracker.fit.dto.requestDto.user.RequestResetPasswordDto;
import com.fitTracker.fit.dto.requestDto.user.RestorePasswordDto;
import com.fitTracker.fit.dto.responseDto.user.AuthResultDto;
import com.fitTracker.fit.dto.responseDto.user.TokenPairDto;
import com.fitTracker.fit.exception.NotFoundException;
import com.fitTracker.fit.mapper.user.UserMapper;
import com.fitTracker.fit.mapper.user.UserParamsMapper;
import com.fitTracker.fit.model.Enum.TokenType;
import com.fitTracker.fit.model.user.User;
import com.fitTracker.fit.repository.user.UserParamsRepository;
import com.fitTracker.fit.repository.user.UserRepository;
import com.fitTracker.fit.service.jwt.JWTService;
import com.fitTracker.fit.service.mail.MailService;
import com.fitTracker.fit.service.user.interfaces.UserService;
import com.fitTracker.fit.validation.auth.interfaces.AuthInfoValidator;
import com.fitTracker.fit.validation.user.interfaces.UserChecker;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

import static com.fitTracker.fit.util.ErrorTemplates.USER_NOT_FOUND;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthInfoValidator authInfoValidator;
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserChecker userChecker;

    private final MailService mailService;
    private final MailConfig mailConfig;

    @Override
    public User getById(Long id) {
        String path = mailConfig.getPasswordPath();
        mailService.sendEMail("anastasia.asi@yandex.ru", "Test mail", "mail message");
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
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
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND, "email", email));
    }

    @Transactional
    @Override
    public AuthResultDto changePassword(ChangePasswordDto changePasswordDto) {
        String email = jwtService.extractUserName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND, "email", email));

        authInfoValidator.throwIfNotValidPassword(changePasswordDto.getOldPassword(), user.getPassword());

        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.saveAndFlush(user);

        TokenPairDto tokens = jwtService.generateTokenPair(user);
        return AuthResultDto.builder()
                .tokenPair(tokens)
                .user(userMapper.toDto(user))
                .build();
    }

    @Transactional
    @Override
    public void requestPasswordReset(RequestResetPasswordDto resetPasswordDto) {
            userChecker.checkIfExistsByEmail(resetPasswordDto.getEmail());
            final String link = "http://localhost:8080}/password-reset";

            String resetToken = jwtService.generateToken(new HashMap<>(), resetPasswordDto.getEmail(), TokenType.RESET);
            String resetLink = link + "%s?token=%s";


    }

    @Transactional
    @Override
    public void resetPassword(RestorePasswordDto restorePasswordDto) {

    }
}
