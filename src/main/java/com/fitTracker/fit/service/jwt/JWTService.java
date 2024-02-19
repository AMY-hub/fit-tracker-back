package com.fitTracker.fit.service.jwt;

import com.fitTracker.fit.dto.responseDto.user.TokenPairDto;
import com.fitTracker.fit.model.Enum.TokenType;
import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.function.Function;

public interface JWTService {
    String generateRefreshToken(UserDetails userDetails);

    TokenPairDto generateTokenPair(UserDetails userDetails);

    TokenPairDto refreshTokenPair(String token);

    String generateToken(
            Map<String, Objects> extraClaims,
            String email,
            TokenType tokenType
    );

    void validateToken(String token, TokenType tokenType, UserDetails userDetails);

    void validateToken(String token, TokenType tokenType);

    String extractUserName(String token);
    String extractUserName();
    <T> T extractClaim(String token, Function<Claims, T> claimResolver);

}
