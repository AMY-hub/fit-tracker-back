package com.fitTracker.fit.config.mail;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@AllArgsConstructor
@Configuration
public class MailSenderConfig {
    private final MailConfig mailConfig;

    @Bean
    public JavaMailSender getJavaMailSender() throws IOException {
        String password = new ClassPathResource(mailConfig.getPasswordPath()).getContentAsString(Charset.defaultCharset());
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailConfig.getHost());
        mailSender.setPort(mailConfig.getPort());

        mailSender.setUsername(mailConfig.getUsername());
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
