package com.group8.library_management.service.impl;

import com.group8.library_management.entity.User;
import com.group8.library_management.entity.Verification;
import com.group8.library_management.repository.UserRepository;
import com.group8.library_management.repository.VerificationRepository;
import com.group8.library_management.service.VerificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VerificationServiceImpl implements VerificationService {
    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;

    public VerificationServiceImpl(VerificationRepository verificationRepository, UserRepository userRepository) {
        this.verificationRepository = verificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createVerification(Verification verification, String token) {
        verification.setToken(token);
        verificationRepository.save(verification);
    }

    @Override
    public void validateVerification(String token) {
        Verification verification = verificationRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid verification token"));
        if (verification.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Verification token has expired");
        }

        verification.setVerified(true);
        verificationRepository.save(verification);

        User user = userRepository.findById(verification.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setActivationDate(LocalDateTime.now());
        userRepository.save(user);
    }
}
