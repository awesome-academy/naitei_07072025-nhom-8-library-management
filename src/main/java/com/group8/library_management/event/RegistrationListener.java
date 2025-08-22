package com.group8.library_management.event;

import com.group8.library_management.entity.User;
import com.group8.library_management.entity.Verification;
import com.group8.library_management.repository.UserRepository;
import com.group8.library_management.service.EmailService;
import com.group8.library_management.service.VerificationService;
import com.group8.library_management.service.impl.VerificationServiceImpl;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Value("${app.verification.expire-hours}")
    private int verificationExpireHour;

    @Value("${app.verification.url}")
    private String verificationUrl;

    @Value("${api.version}")
    private String apiVersion;

    private final VerificationService verificationService;
    private final EmailService emailService;

    public RegistrationListener(VerificationServiceImpl verificationService, EmailService emailService, UserRepository userRepository) {
        this.verificationService = verificationService;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(@NonNull OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        Verification verification = new Verification();
        verification.setUserId(user.getId());
        verification.setExpiredAt(LocalDateTime.now().plusHours(verificationExpireHour));
        String token = UUID.randomUUID().toString();
        verificationService.createVerification(verification, token);

        String recipientAddress = user.getEmail();
        String subject = "Email Verification";
        String confirmationUrl = verificationUrl + "/api/" + apiVersion + "/auth/verify-email?token=" + token;

        // Lấy locale từ request hiện tại, mặc định EN nếu không có
        Locale locale = LocaleContextHolder.getLocale();

        try {
            emailService.sendVerificationEmail(recipientAddress, subject, confirmationUrl, locale);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send verification email", e);
        }
    }
}
