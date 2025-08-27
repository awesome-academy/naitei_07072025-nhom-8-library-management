package com.group8.library_management.repository;

import com.group8.library_management.entity.BorrowRequestDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRequestDetailRepository extends JpaRepository<BorrowRequestDetail, Integer> {
    List<BorrowRequestDetail> findByBorrowRequestId(Integer borrowRequestId);
}
