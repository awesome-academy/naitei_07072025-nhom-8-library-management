package com.group8.library_management.dto.response;

import java.time.LocalDate;

public record DueSoonBookRes(
        Integer borrowRecordId,
        String fullName, // user's fullname
        String bookTitle,
        LocalDate dueDate,
        LocalDate returnedAt,
        Integer copyId,
        Boolean isOverdue,
        Integer daysUntilDue
) {
}
