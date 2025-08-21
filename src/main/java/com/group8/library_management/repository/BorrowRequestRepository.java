package com.group8.library_management.repository;

import com.group8.library_management.entity.BorrowRequest;
import com.group8.library_management.enums.BorrowRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRequestRepository extends JpaRepository<BorrowRequest, Integer> {
    Long countAllByStatus(BorrowRequestStatus status);
}
