package com.fitTracker.fit.service.mail;

public interface MailService {
    void sendSimpleMail(String to, String subject, String text);

    void sendMailWithHtmlContent(String to, String subject, String content);
}
