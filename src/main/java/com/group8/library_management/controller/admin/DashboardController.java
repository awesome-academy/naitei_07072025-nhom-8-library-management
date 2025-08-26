package com.group8.library_management.controller.admin;

import com.group8.library_management.service.BookService;
import com.group8.library_management.service.BorrowRecordService;
import com.group8.library_management.service.BorrowRequestService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final BookService bookService;
    private final BorrowRequestService borrowRequestService;
    private final BorrowRecordService borrowRecordService;

    public DashboardController(BookService bookService, BorrowRequestService borrowRequestService, BorrowRecordService borrowRecordService) {
        this.bookService = bookService;
        this.borrowRequestService = borrowRequestService;
        this.borrowRecordService = borrowRecordService;
    }

    // Trang dashboard sau khi login thành công
    @GetMapping("/admin/dashboard")
    public String getDashboardPage(Model model, HttpServletRequest request) {
        model.addAttribute("totalBooks", bookService.countBooks());
        model.addAttribute("activeBorrows", borrowRecordService.countBorrowingRecords());
        model.addAttribute("pendingRequests", borrowRequestService.countPendingBorrowRequests());
        model.addAttribute("dueSoonRecords", borrowRecordService.countDueSoonRecords());
        model.addAttribute("dueSoonBooks", borrowRecordService.getDueSoonBooks());
        model.addAttribute("request", request);
        return "dashboard";
    }
}