package com.group8.library_management.mapper;

import com.group8.library_management.dto.response.BorrowHistoryResponse;
import com.group8.library_management.entity.BorrowRecord;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class BorrowHistoryMapper {
    public BorrowHistoryResponse toResponse(BorrowRecord entity) {
        String bookTitle = Optional.ofNullable(entity.getBorrowRequestDetail())
        .map(detail -> detail.getCopy())
        .map(copy -> copy.getBook())
        .map(book -> book.getTitle())
        .orElse(null);
        return BorrowHistoryResponse.builder()
            .borrowedAt(entity.getBorrowedAt())
            .dueDate(entity.getDueDate())
            .returnedAt(entity.getReturnedAt())
            .status(entity.getStatus().name())
            .bookTitle(bookTitle)
            .build();
    }
}
