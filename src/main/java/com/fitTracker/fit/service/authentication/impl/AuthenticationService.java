package com.fitTracker.fit.service.authentication.impl;

import com.fitTracker.fit.dto.user.*;
import com.fitTracker.fit.exception.UserNotFoundException;
import com.fitTracker.fit.mapper.user.UserParamsMapper;
import com.fitTracker.fit.mapper.user.UserMapper;
import com.fitTracker.fit.model.user.User;
import com.fitTracker.fit.model.user.UserParams;
import com.fitTracker.fit.model.user.UserRole;
import com.fitTracker.fit.repository.user.UserParamsRepository;
import com.fitTracker.fit.repository.user.UserRepository;
import com.fitTracker.fit.repository.user.UserRoleRepository;
import com.fitTracker.fit.service.authentication.interfaces.IAuthenticationService;
import com.fitTracker.fit.service.jwt.JWTService;
import com.fitTracker.fit.validation.user.impl.AuthInfoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final AuthInfoValidator authInfoValidator;
    private final JWTService jwtService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserParamsRepository userDetailsRepository;
    private final UserParamsMapper userDetailsMapper;
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserDto register(RegistrationDto registrationDto) {
            UserParams details = userDetailsMapper.toModel(registrationDto.getDetails());
            UserRole userRole = userRoleRepository.findById(1L).orElseThrow();

            User user = User.builder()
                    .createdAt(OffsetDateTime.now())
                    .role(userRole)
                    .firstname(registrationDto.getFirstname())
                    .lastname(registrationDto.getLastname())
                    .email(registrationDto.getEmail())
                    .password(passwordEncoder.encode(registrationDto.getPassword()))
                    .details(details)
                    .build();
            details.setUser(user);
            user.setDetails(details);
            userDetailsRepository.saveAndFlush(details);
            User newUser = userRepository.saveAndFlush(user);

            return userMapper.toDto(newUser);
    }

    @Override
    public AuthResultDto authenticate(AuthDto authDto) throws IOException {
        User user = userRepository.findByEmail(authDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("email", authDto.getEmail()));

        authInfoValidator.throwIfNotValidPassword(authDto.getPassword(), user.getPassword());

        TokenPairDto tokens = jwtService.generateTokenPair(user);
        return AuthResultDto.builder()
                .tokenPair(tokens)
                .user(userMapper.toDto(user))
                .build();
    }

    @Override
    public TokenPairDto refreshTokens(TokenPairDto tokenPairDto) {
        return null;
    }
}
