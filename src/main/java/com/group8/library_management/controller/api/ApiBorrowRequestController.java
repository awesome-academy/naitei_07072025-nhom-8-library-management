package com.group8.library_management.controller.api;

import com.group8.library_management.dto.request.BorrowRequestReq;
import com.group8.library_management.dto.response.BaseAPIRes;
import com.group8.library_management.service.BorrowRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/${api.version}/borrow-request")
@RequiredArgsConstructor
public class ApiBorrowRequestController {

    private final BorrowRequestService borrowRequestService;

    @PostMapping
    public ResponseEntity<?> createBorrowRequest(
            @RequestBody BorrowRequestReq borrowRequestReq,
            @RequestHeader("Authorization") String tokenHeader
    ) {

        String rawToken = tokenHeader.startsWith("Bearer ")
                ? tokenHeader.substring(7)
                : tokenHeader;
        System.out.println("rawToken: " + rawToken);

        String message = borrowRequestService.createBorrowRequest(borrowRequestReq.getBookIds(), rawToken);
        return ResponseEntity.ok(
                BaseAPIRes.success(HttpStatus.OK.value(), message, null)
        );
    }
}
