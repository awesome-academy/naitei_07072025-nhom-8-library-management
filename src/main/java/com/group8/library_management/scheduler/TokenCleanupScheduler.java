package com.group8.library_management.scheduler;

import com.group8.library_management.repository.TokenBlacklistRepository;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

public class TokenCleanupScheduler {
    private final TokenBlacklistRepository tokenBlacklistRepository;

    public TokenCleanupScheduler(TokenBlacklistRepository tokenBlacklistRepository) {
        this.tokenBlacklistRepository = tokenBlacklistRepository;
    }

    // Schedule tự động xóa các token đã hết hạn mỗi ngày vào lúc 00:00
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanUpExpiredTokens() {
        tokenBlacklistRepository.deleteAllByExpiredAtBefore(LocalDateTime.now());
    }
}
