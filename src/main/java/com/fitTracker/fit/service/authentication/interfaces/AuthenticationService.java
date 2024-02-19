package com.fitTracker.fit.service.authentication.interfaces;

import com.fitTracker.fit.dto.responseDto.user.UserDto;
import com.fitTracker.fit.dto.requestDto.user.LoginDto;
import com.fitTracker.fit.dto.responseDto.user.AuthResultDto;
import com.fitTracker.fit.dto.requestDto.user.RegistrationDto;
import com.fitTracker.fit.dto.responseDto.user.TokenPairDto;

import java.io.IOException;

public interface AuthenticationService {
    UserDto register(RegistrationDto registrationDto);

    AuthResultDto authenticate(LoginDto authDto) throws IOException;

    TokenPairDto refreshTokens(TokenPairDto tokenPairDto);
}
