package com.fitTracker.fit.service.mail;

public interface MailService {
    void sendSimpleMail(String to, String subject, String text);

    void sendMailWithContent(String to, String subject, String text, String content);
}
