package com.group8.library_management.controller.api;

import com.group8.library_management.dto.response.BaseAPIRes; 
import org.springframework.context.MessageSource;
import java.util.Locale;
import jakarta.servlet.http.HttpServletRequest;
import com.group8.library_management.service.BorrowRecordService;
import com.group8.library_management.dto.response.BorrowHistoryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;
import com.group8.library_management.dto.response.PageRes;


@RestController
@RequestMapping("/api/${api.version}/borrow-history")
public class BorrowHistoryController {

    private final BorrowRecordService borrowRecordService;
    private final MessageSource messageSource;

    public BorrowHistoryController(BorrowRecordService borrowRecordService, MessageSource messageSource) {
        this.borrowRecordService = borrowRecordService;
        this.messageSource = messageSource;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getBorrowHistoryByUserId(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "DESC") String sortDir,
            HttpServletRequest request
    ) {
        size = Math.max(1, Math.min(size, 100)); 
        Locale locale = RequestContextUtils.getLocale(request);

    PageRes<BorrowHistoryResponse> result =
        borrowRecordService.getBorrowHistoryByUserId(userId, page, size, sortDir);

        String msg = (result == null || result.getContent().isEmpty())
                ? messageSource.getMessage("borrow.no_history", null, locale)
                : messageSource.getMessage("borrow.success", null, locale);

        return ResponseEntity.ok(BaseAPIRes.success(HttpStatus.OK.value(), msg, result));
    }
}
