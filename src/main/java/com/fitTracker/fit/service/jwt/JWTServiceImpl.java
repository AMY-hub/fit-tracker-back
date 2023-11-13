package com.fitTracker.fit.service.jwt;

import com.fitTracker.fit.config.security.JWTConfig;
import com.fitTracker.fit.dto.responseDto.user.TokenPairDto;
import com.fitTracker.fit.exception.BadRequestException;
import com.fitTracker.fit.exception.NotFoundException;
import com.fitTracker.fit.model.Enum.TokenType;
import com.fitTracker.fit.model.user.RefreshToken;
import com.fitTracker.fit.model.user.User;
import com.fitTracker.fit.repository.user.RefreshTokenRepository;
import com.fitTracker.fit.repository.user.UserRepository;
import com.fitTracker.fit.util.TokenResolver;
import io.jsonwebtoken.*;
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

import static com.fitTracker.fit.util.ErrorTemplates.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

    private final JWTConfig jwtConfig;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenResolver tokenResolver;

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        String token = generateToken(new HashMap<>(), userDetails.getUsername(), TokenType.REFRESH);
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND, "email", userDetails.getUsername()));
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

    @Override
    public TokenPairDto generateTokenPair(UserDetails userDetails) {
        TokenPairDto tokens = new TokenPairDto();
        tokens.setAccessToken(generateToken(new HashMap<>(), userDetails.getUsername(), TokenType.ACCESS));
        tokens.setRefreshToken(generateRefreshToken(userDetails));

        return tokens;
    }

    @Override
    public TokenPairDto refreshTokenPair(String token) {
        if (!isTokenValid(token, TokenType.REFRESH)) {
            throw new BadRequestException("Invalid token");
        } else if (isTokenExpired(token)) {
            throw new BadRequestException("Expired token");
        }
        final String userName = extractUserName(token);
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND, "email", userName));

        checkRefreshTokenByUserId(user.getId(), token);

        return generateTokenPair(user);
    }

    @Override
    public String generateToken(
            Map<String, Objects> extraClaims,
            String email,
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
                    .setSubject(email)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(expDate)
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (IOException e) {
            return null;
        }

    }

    @Override
    public void validateToken(String token, TokenType tokenType, UserDetails userDetails) {
        if (!isTokenValid(token, tokenType, userDetails)) {
            throw new BadRequestException("Invalid token");
        } else if (isTokenExpired(token)) {
            throw new BadRequestException("Token expired");
        }
    }

    private boolean isTokenValid(String token, TokenType tokenType, UserDetails userDetails) {
        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(getSignInKey()).build();
            Jwt parsedJwt = parser.parse(token);
            final boolean sameType = tokenType.toString().equals(parsedJwt.getHeader().get("tokenType"));
            final String userName = extractUserName(token);
            return sameType && userName.equals(userDetails.getUsername());
        } catch (IOException ex) {
            return false;
        }
    }

    private boolean isTokenValid(String token, TokenType tokenType) {
        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(getSignInKey()).build();
            Jwt parsedJwt = parser.parse(token);
            return tokenType.equals(parsedJwt.getHeader().get("tokenType"));
        } catch (IOException ex) {
            return false;
        }
    }

    private void checkRefreshTokenByUserId(Long id, String token) {
        Optional<RefreshToken> oldToken = refreshTokenRepository.findByUserId(id);
        if(oldToken.isPresent() && !oldToken.get().getToken().equals(token)) {
            throw new BadRequestException("Invalid Refresh token");
        }
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String extractUserName() {
        return extractClaim(tokenResolver.getTokenFromRequest(), Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        Claims claims = extractAllClaims(token);
        T result = claimResolver.apply(claims);
        if(result == null) {
            throw new BadRequestException("Could not get claim form token");
        }

        return  result;
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
