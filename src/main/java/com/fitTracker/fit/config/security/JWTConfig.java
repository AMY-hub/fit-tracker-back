package com.fitTracker.fit.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JWTConfig {

    private String jwtKeyFilePath;

    private int accessTokenExpirationTime;

    private int refreshTokenExpirationTime;

    private int resetTokenExpirationTime;
}