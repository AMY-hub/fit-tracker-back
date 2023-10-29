package com.fitTracker.fit.service.jwt;

import com.fitTracker.fit.config.security.JWTConfig;
import com.fitTracker.fit.dto.user.TokenPairDto;
import com.fitTracker.fit.model.Enum.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
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
    public String generateToken(UserDetails userDetails, TokenType tokenType) throws IOException {
        return generateToken(new HashMap<>(), userDetails, tokenType);
    }

    public TokenPairDto generateTokenPair(UserDetails userDetails) throws IOException {
        TokenPairDto tokens = new TokenPairDto();
        tokens.setAccessToken(generateToken(new HashMap<>(), userDetails, TokenType.ACCESS));
        tokens.setRefreshToken(generateToken(new HashMap<>(), userDetails, TokenType.REFRESH));

        return tokens;
    }

    public String generateToken(
            Map<String, Objects> extraClaims,
            UserDetails userDetails,
            TokenType tokenType
    ) throws IOException {
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
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) throws IOException {
        final String userName = extractUserName(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) throws IOException {
        Date expirationDate = extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    public String extractUserName(String token) throws IOException {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) throws IOException {
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) throws IOException {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() throws IOException {
        byte[] keyBytes = new ClassPathResource(jwtConfig.getJwtKeyFilePath()).getInputStream().readAllBytes();
//        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
