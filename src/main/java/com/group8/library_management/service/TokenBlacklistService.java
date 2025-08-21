package com.group8.library_management.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

public interface TokenBlacklistService {
    public void tokenBlackList(String token, LocalDateTime expiredAt);

    public boolean isTokenBlacklisted(String token);
}
