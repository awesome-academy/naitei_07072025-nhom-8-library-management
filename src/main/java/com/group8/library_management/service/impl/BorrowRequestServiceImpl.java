package com.group8.library_management.service.impl;

import com.group8.library_management.entity.BorrowRequest;
import com.group8.library_management.entity.BorrowRequestDetail;
import com.group8.library_management.entity.Copy;
import com.group8.library_management.entity.User;
import com.group8.library_management.enums.BorrowRequestDetailStatus;
import com.group8.library_management.enums.BorrowRequestStatus;
import com.group8.library_management.repository.BorrowRequestDetailRepository;
import com.group8.library_management.repository.BorrowRequestRepository;
import com.group8.library_management.repository.CopyRepository;
import com.group8.library_management.repository.UserRepository;
import com.group8.library_management.service.BorrowRequestService;
import com.group8.library_management.service.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BorrowRequestServiceImpl implements BorrowRequestService {

    private final BorrowRequestRepository borrowRequestRepository;
    private final BorrowRequestDetailRepository borrowRequestDetailRepository;
    private final CopyRepository copyRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public BorrowRequestServiceImpl(
            BorrowRequestRepository borrowRequestRepository,
            BorrowRequestDetailRepository borrowRequestDetailRepository,
            CopyRepository copyRepository,
            JwtService jwtService,
            UserRepository userRepository
    ) {
        this.borrowRequestRepository = borrowRequestRepository;
        this.borrowRequestDetailRepository = borrowRequestDetailRepository;
        this.copyRepository = copyRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public long countPendingBorrowRequests() {
        return borrowRequestRepository.countAllByStatus(BorrowRequestStatus.PENDING);
    }

    @Transactional
    public String createBorrowRequest(List<Integer> bookIds, String token) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = (principal instanceof UserDetails)
                ? ((UserDetails) principal).getUsername()
                : principal.toString();

        // Lấy User entity từ DB
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Tạo borrow_request
        BorrowRequest borrowRequest = new BorrowRequest();
        borrowRequest.setUser(user);
        borrowRequest.setFromDate(LocalDate.now());
        borrowRequest.setToDate(LocalDate.now().plusDays(14));
        borrowRequest.setStatus(BorrowRequestStatus.PENDING);

        borrowRequestRepository.saveAndFlush(borrowRequest); // Lưu để có ID

        List<BorrowRequestDetail> details = new ArrayList<>();
        List<Integer> unavailableBooks = new ArrayList<>();

        for (Integer bookId : bookIds) {
            Copy availableCopy = copyRepository.findAvailableCopyByBookId(bookId);
            if (availableCopy == null) {
                unavailableBooks.add(bookId);
                continue;
            }

            BorrowRequestDetail detail = new BorrowRequestDetail();
            detail.setBorrowRequest(borrowRequest);
            detail.setCopy(availableCopy);
            detail.setStatus(BorrowRequestDetailStatus.PENDING);

            details.add(detail);
        }

        borrowRequestDetailRepository.saveAll(details);

        if (!unavailableBooks.isEmpty()) {
            return "Một số sách không khả dụng: " + unavailableBooks;
        }
        return "Yêu cầu mượn sách đã được tạo thành công";
    }
}
