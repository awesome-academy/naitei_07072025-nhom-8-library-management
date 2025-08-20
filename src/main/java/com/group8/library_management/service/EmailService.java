package com.group8.library_management.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private String loadEmailTemplate(String templateName) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/send-mail/" + templateName);
        try (InputStream inputStream = resource.getInputStream()) {
            return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        }
    }

    public void sendVerificationEmail(String to, String subject, String verificationLink, Locale locale) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // get locale (default EN)
            String lang = locale.getLanguage().equalsIgnoreCase("vi") ? "VI" : "EN";
            String templateFile = "verification-template_" + lang + ".html";

            // Load and replace placeholder
            String htmlContent = loadEmailTemplate(templateFile);
            htmlContent = htmlContent.replace("{{VERIFICATION_LINK}}", verificationLink);
            htmlContent = htmlContent.replace("{{USER_EMAIL}}", to);

            helper.setTo(to);
            helper.setSubject(subject + " - Library Management");
            helper.setText(htmlContent, true);
            helper.setFrom("noreply@librarymanagement.com", "Library Management Team");

            mailSender.send(message);
            logger.info("Verification email sent to: {} with lang: {}", to, lang);
        } catch (MessagingException | IOException e) {
            logger.error("Failed to send email to {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("Failed to send verification email", e);
        }
    }
}
