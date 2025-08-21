package com.group8.library_management.service.impl;

import com.group8.library_management.dto.response.DueSoonBookRes;
import com.group8.library_management.enums.BorrowRecordStatus;
import com.group8.library_management.repository.BorrowRecordRepository;
import com.group8.library_management.service.BorrowRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowRecordServiceImpl implements BorrowRecordService {
    private final BorrowRecordRepository borrowRecordRepository;

    public BorrowRecordServiceImpl(BorrowRecordRepository borrowRecordRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
    }

    @Override
    public long coundDueSoonRecords() {
        return borrowRecordRepository.countByStatusAndDueDate(BorrowRecordStatus.BORROWED, LocalDate.now().plusDays(1));
    }

    @Override
    public long countBorrowingRecords() {
        return borrowRecordRepository.countByStatus(BorrowRecordStatus.BORROWED);
    }

    @Override
    public long countOverdueRecords() {
        return borrowRecordRepository.countByStatusAndDueDate(BorrowRecordStatus.BORROWED, LocalDate.now().minusDays(1));
    }

    @Override
    public List<DueSoonBookRes> getDueSoonBooks() {
        LocalDate currentDate = LocalDate.now();
        LocalDate limitDate = LocalDate.now().plusDays(1);
        return borrowRecordRepository.findDueSoonBooks(currentDate, limitDate);
    }
}
