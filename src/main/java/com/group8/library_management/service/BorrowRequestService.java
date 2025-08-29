package com.group8.library_management.service;

import jakarta.transaction.Transactional;

import java.util.List;

public interface BorrowRequestService {
    long countPendingBorrowRequests();
    @Transactional
    public String createBorrowRequest(List<Integer> bookIds, String token);
}
