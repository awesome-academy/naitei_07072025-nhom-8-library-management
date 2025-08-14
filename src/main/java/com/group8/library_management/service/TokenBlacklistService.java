package com.group8.library_management.service;

import com.group8.library_management.entity.TokenBlackList;
import com.group8.library_management.repository.TokenBlacklistRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenBlacklistService {
    private final TokenBlacklistRepository repository;

    public TokenBlacklistService(TokenBlacklistRepository repository) {
        this.repository = repository;
    }

    public void tokenBlackList(String token, LocalDateTime expiredAt) {
        TokenBlackList blacklistedToken = new TokenBlackList();
        blacklistedToken.setToken(token);
        blacklistedToken.setExpiredAt(expiredAt);
        repository.save(blacklistedToken);
    }

    public boolean isTokenBlacklisted(String token) {
        return repository.existsByToken(token);
    }
}
