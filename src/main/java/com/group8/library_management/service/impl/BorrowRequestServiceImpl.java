package com.group8.library_management.service.impl;

import com.group8.library_management.enums.BorrowRequestStatus;
import com.group8.library_management.repository.BorrowRequestRepository;
import com.group8.library_management.service.BorrowRequestService;
import org.springframework.stereotype.Service;

@Service
public class BorrowRequestServiceImpl implements BorrowRequestService {

    private final BorrowRequestRepository borrowRequestRepository;

    public BorrowRequestServiceImpl(BorrowRequestRepository borrowRequestRepository) {
        this.borrowRequestRepository = borrowRequestRepository;
    }

    @Override
    public long countPendingBorrowRequests() {
        return borrowRequestRepository.countAllByStatus(BorrowRequestStatus.PENDING);
    }
}
