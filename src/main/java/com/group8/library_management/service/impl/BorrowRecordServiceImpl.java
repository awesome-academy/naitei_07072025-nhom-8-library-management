package com.group8.library_management.service.impl;

import com.group8.library_management.dto.response.DueSoonBookRes;
import com.group8.library_management.dto.response.BorrowHistoryResponse;
import com.group8.library_management.dto.response.PageRes;
import com.group8.library_management.entity.BorrowRecord;
import com.group8.library_management.enums.BorrowRecordStatus;
import com.group8.library_management.mapper.BorrowHistoryMapper;
import com.group8.library_management.repository.BorrowRecordRepository;
import com.group8.library_management.service.BorrowRecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class BorrowRecordServiceImpl implements BorrowRecordService {
    private final BorrowRecordRepository borrowRecordRepository;
    private final BorrowHistoryMapper borrowHistoryMapper;

    public BorrowRecordServiceImpl(BorrowRecordRepository borrowRecordRepository, BorrowHistoryMapper borrowHistoryMapper) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.borrowHistoryMapper = borrowHistoryMapper;
    }
    @Override
    public PageRes<BorrowHistoryResponse> getBorrowHistoryByUserId(
        Integer userId, int page, int size, String sortDir) {
    Sort.Direction direction = sortDir.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "borrowedAt"));
    Page<BorrowRecord> borrowRecordPage = borrowRecordRepository.findByUserId(userId, pageable);
    List<BorrowHistoryResponse> records = borrowRecordPage.getContent()
        .stream()
        .map(borrowHistoryMapper::toResponse)
        .collect(java.util.stream.Collectors.toList());
    return new PageRes<>(
        records,
        borrowRecordPage.getNumber(),
        borrowRecordPage.getSize(),
        borrowRecordPage.getTotalElements(),
        borrowRecordPage.getTotalPages()
    );
    }

    @Override
    public long countDueSoonRecords() {
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
