package com.group8.library_management.repository;

import com.group8.library_management.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, Integer> {
    Optional<Verification> findByToken(String token);

}
