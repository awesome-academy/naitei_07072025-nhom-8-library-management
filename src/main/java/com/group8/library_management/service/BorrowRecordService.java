package com.group8.library_management.service;

import com.group8.library_management.dto.response.DueSoonBookRes;

import java.util.List;

public interface BorrowRecordService {
    long coundDueSoonRecords();
    long countBorrowingRecords();
    long countOverdueRecords();
    List<DueSoonBookRes> getDueSoonBooks();
}
