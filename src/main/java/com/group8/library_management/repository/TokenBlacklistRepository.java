package com.group8.library_management.repository;

import com.group8.library_management.entity.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlackList, Long> {
    boolean existsByToken(String token);
    void deleteAllByExpiredAtBefore(LocalDateTime time);
}
