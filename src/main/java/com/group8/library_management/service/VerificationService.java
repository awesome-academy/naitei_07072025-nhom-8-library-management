package com.group8.library_management.service;

import com.group8.library_management.entity.Verification;

public interface VerificationService {
    void createVerification(Verification verification, String token);
    void validateVerification(String token);
}
