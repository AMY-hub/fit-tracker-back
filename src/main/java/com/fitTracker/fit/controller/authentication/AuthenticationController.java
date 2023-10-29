package com.fitTracker.fit.controller.authentication;

import com.fitTracker.fit.dto.user.*;
import com.fitTracker.fit.mapper.user.UserMapper;
import com.fitTracker.fit.service.authentication.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegistrationDto registrationDto) {
        return ResponseEntity.ok(authenticationService.register(registrationDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResultDto> register(@RequestBody AuthDto authDto) throws IOException {
        return ResponseEntity.ok(authenticationService.authenticate(authDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenPairDto> register(@RequestBody TokenPairDto tokenPairDto) {
        return ResponseEntity.ok(authenticationService.refreshTokens(tokenPairDto));
    }
}
