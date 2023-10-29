package com.fitTracker.fit.service.authentication.interfaces;

import com.fitTracker.fit.dto.user.*;

import java.io.IOException;

public interface IAuthenticationService {
    UserDto register(RegistrationDto registrationDto);

    AuthResultDto authenticate(AuthDto authDto) throws IOException;

    TokenPairDto refreshTokens(TokenPairDto tokenPairDto);
}
