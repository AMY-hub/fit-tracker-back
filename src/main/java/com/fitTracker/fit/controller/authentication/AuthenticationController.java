package com.fitTracker.fit.controller.authentication;

import com.fitTracker.fit.dto.responseDto.user.UserDto;
import com.fitTracker.fit.dto.requestDto.user.AuthDto;
import com.fitTracker.fit.dto.responseDto.user.AuthResultDto;
import com.fitTracker.fit.dto.requestDto.user.RegistrationDto;
import com.fitTracker.fit.dto.responseDto.user.TokenPairDto;
import com.fitTracker.fit.service.authentication.impl.AuthenticationServiceImpl;
import jakarta.validation.Valid;
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
    private final AuthenticationServiceImpl authenticationService;
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegistrationDto  registrationDto) {
        return ResponseEntity.ok(authenticationService.register(registrationDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResultDto> register(@RequestBody @Valid AuthDto authDto) throws IOException {
        return ResponseEntity.ok(authenticationService.authenticate(authDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenPairDto> register(@RequestBody @Valid TokenPairDto tokenPairDto) {
        return ResponseEntity.ok(authenticationService.refreshTokens(tokenPairDto));
    }
}
