package com.group8.library_management.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BorrowRequestReq {

    @NotEmpty(message = "{borrow_request.books.not_empty}")
    private List<Integer> bookIds; // Danh sách book_id mà user muốn mượn

    @NotNull(message = "{borrow_request.from_date.not_null}")
    private LocalDate fromDate;

    @NotNull(message = "{borrow_request.to_date.not_null}")
    private LocalDate toDate;
}
