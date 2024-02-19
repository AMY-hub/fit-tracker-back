package com.fitTracker.fit.config.link;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "link")
public class LinkConfig {
    private String resetPasswordURL;
}
