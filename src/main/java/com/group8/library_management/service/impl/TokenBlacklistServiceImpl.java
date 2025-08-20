package com.group8.library_management.service.impl;

import com.group8.library_management.entity.TokenBlackList;
import com.group8.library_management.repository.TokenBlacklistRepository;
import com.group8.library_management.service.TokenBlacklistService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenBlacklistServiceImpl implements TokenBlacklistService {
    private final TokenBlacklistRepository repository;

    public TokenBlacklistServiceImpl(TokenBlacklistRepository repository) {
        this.repository = repository;
    }

    @Override
    public void tokenBlackList(String token, LocalDateTime expiredAt) {
        TokenBlackList blacklistedToken = new TokenBlackList();
        blacklistedToken.setToken(token);
        blacklistedToken.setExpiredAt(expiredAt);
        repository.save(blacklistedToken);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return repository.existsByToken(token);
    }
}
