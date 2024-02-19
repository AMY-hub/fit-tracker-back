package com.fitTracker.fit.service.mail.impl;

import com.fitTracker.fit.config.mail.MailConfig;
import com.fitTracker.fit.exception.InternalServerException;
import com.fitTracker.fit.service.mail.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private final MailConfig mailConfig;
    @Override
    public void sendSimpleMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailConfig.getUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }

    @Override
    public void sendMailWithHtmlContent(String to, String subject, String content) {
        try {
            MimeMessage message = buildMimeMessage(to, subject);
            message.setContent(content, "text/html; charset=UTF-8");
            javaMailSender.send(message);
        } catch (MessagingException e) {
            String errMessage = String.format("Exception when trying to send MIME message to %s with subject %s", to, subject);
            throw new InternalServerException(errMessage);
        }
    }

    private MimeMessage buildMimeMessage(String to, String subject) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mailMessage = new MimeMessageHelper(message, "utf-8");
        mailMessage.setFrom(mailConfig.getUsername());
        mailMessage.setTo(to);
        message.setSubject(subject);
        return message;
    }
}
