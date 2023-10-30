package com.fitTracker.fit.service.jwt;

import com.fitTracker.fit.config.security.JWTConfig;
import com.fitTracker.fit.dto.user.TokenPairDto;
import com.fitTracker.fit.exception.UserNotFoundException;
import com.fitTracker.fit.model.Enum.TokenType;
import com.fitTracker.fit.model.user.RefreshToken;
import com.fitTracker.fit.model.user.User;
import com.fitTracker.fit.repository.user.RefreshTokenRepository;
import com.fitTracker.fit.repository.user.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTService {
    private final JWTConfig jwtConfig;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public String generateRefreshToken(UserDetails userDetails) {
        String token = generateToken(new HashMap<>(), userDetails, TokenType.REFRESH);
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("email", userDetails.getUsername()));
        Optional<RefreshToken> oldToken = refreshTokenRepository.findByUserId(user.getId());

        if(oldToken.isPresent()) {
            oldToken.get().setToken(token);
            refreshTokenRepository.saveAndFlush(oldToken.get());
        } else {
            RefreshToken refreshToken = RefreshToken
                    .builder()
                    .token(token)
                    .user(user)
                    .build();

            refreshTokenRepository.saveAndFlush(refreshToken);
        }

        return token;
    }

    public TokenPairDto generateTokenPair(UserDetails userDetails) {
        TokenPairDto tokens = new TokenPairDto();
        tokens.setAccessToken(generateToken(new HashMap<>(), userDetails, TokenType.ACCESS));
        tokens.setRefreshToken(generateRefreshToken(userDetails));

        return tokens;
    }

    public TokenPairDto refreshTokenPair(String token) {
        try {
            validateToken(token, TokenType.REFRESH);
            final String userName = extractUserName(token);
            User user = userRepository.findByEmail(userName)
                    .orElseThrow(() -> new UserNotFoundException("email", userName));
            Optional<RefreshToken> oldToken = refreshTokenRepository.findByUserId(user.getId());
            if(oldToken.isPresent() && !oldToken.get().getToken().equals(token)) {
                throw new BadCredentialsException("Invalid Refresh tolen");
            }

            return generateTokenPair(user);
        } catch (Exception e) {
            return null;
        }
    }

    public String generateToken(
            Map<String, Objects> extraClaims,
            UserDetails userDetails,
            TokenType tokenType
    ) {
        try {
            Date expDate;
            if(tokenType.equals(TokenType.REFRESH)) {
                expDate = Date.from(Instant.now().plusSeconds(jwtConfig.getRefreshTokenExpirationTime()));
            } else if (tokenType.equals(TokenType.RESET)) {
                expDate = Date.from(Instant.now().plusSeconds(jwtConfig.getResetTokenExpirationTime()));
            } else {
                expDate = Date.from(Instant.now().plusSeconds(jwtConfig.getAccessTokenExpirationTime()));
            }

            return Jwts.builder()
                    .setClaims(extraClaims)
                    .setHeader(Collections.singletonMap("tokenType", tokenType))
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(expDate)
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (IOException e) {
            return null;
        }

    }

    public void validateToken(String token, TokenType tokenType, UserDetails userDetails) {
        if (!isTokenValid(token, tokenType, userDetails)) {

        } else if (isTokenExpired(token)) {

        }
    }

    public void validateToken(String token, TokenType tokenType) {
        if (!isTokenValid(token, tokenType)) {

        } else if (isTokenExpired(token)) {

        }
    }

    public boolean isTokenValid(String token, TokenType tokenType, UserDetails userDetails) {
        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(getSignInKey()).build();
            Jwt parsedJwt = parser.parse(token);
            final boolean sameType = tokenType.equals(parsedJwt.getHeader().get("tokenType"));
            final String userName = extractUserName(token);
            return sameType && userName.equals(userDetails.getUsername());
        } catch (IOException ex) {
            return false;
        }
    }

    public boolean isTokenValid(String token, TokenType tokenType) {
        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(getSignInKey()).build();
            Jwt parsedJwt = parser.parse(token);
            return tokenType.equals(parsedJwt.getHeader().get("tokenType"));
        } catch (IOException ex) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (IOException e) {
            return null;
        }

    }

    private Key getSignInKey() throws IOException {
        byte[] keyBytes = new ClassPathResource(jwtConfig.getJwtKeyFilePath()).getInputStream().readAllBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
