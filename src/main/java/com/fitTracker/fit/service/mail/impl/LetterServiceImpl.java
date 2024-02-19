package com.fitTracker.fit.service.mail.impl;

import com.fitTracker.fit.config.link.LinkConfig;
import com.fitTracker.fit.config.template.TemplateConfig;
import com.fitTracker.fit.service.mail.LetterService;
import com.fitTracker.fit.service.mail.MailService;
import com.fitTracker.fit.service.template.TemplateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class LetterServiceImpl implements LetterService {
    private final MailService mailService;
    private final LinkConfig linkConfig;
    private final TemplateConfig templateConfig;
    private final TemplateService templateService;

    @Override
    public void sendPasswordResetLetter(String token, String email) {
        String url = String.format("%s?token=%s", linkConfig.getResetPasswordURL(), token);
        String templatePath = templateConfig.getBaseLetterTemplatePath();
        String RESET_TITLE = "Password reset for FitTracker app";

        Map<String, String> letterData = new HashMap<>();
        letterData.put("title",  RESET_TITLE);
        letterData.put("text", "You received this email because a password reset was requested for your email address in the FitTracker app.");
        letterData.put("linkValue", "this link");
        letterData.put("linkHref", url);
        letterData.put("resetText", "To reset your password, please follow");

        String template = templateService.compileTemplateFromPath(templatePath, letterData);

        mailService.sendMailWithHtmlContent(email, RESET_TITLE, template);
    }
}
