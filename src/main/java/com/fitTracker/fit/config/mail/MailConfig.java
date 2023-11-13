package com.fitTracker.fit.config.mail;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mail")
public class MailConfig {
    private String username;
    private String passwordPath;
    private String host;
    private int port;
}
