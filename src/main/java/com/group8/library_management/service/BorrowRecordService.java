package com.group8.library_management.service;

import com.group8.library_management.dto.response.DueSoonBookRes;
import com.group8.library_management.dto.response.PageRes;
import com.group8.library_management.dto.response.BorrowHistoryResponse;

import java.util.List;

public interface BorrowRecordService {
    long countDueSoonRecords();
    long countBorrowingRecords();
    long countOverdueRecords();
    List<DueSoonBookRes> getDueSoonBooks();

    PageRes<BorrowHistoryResponse> getBorrowHistoryByUserId(
            Integer userId,
            int page,
            int size,
            String sortDir
    );
}
