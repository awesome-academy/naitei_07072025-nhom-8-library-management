package com.group8.library_management.service.impl;

import com.group8.library_management.entity.BorrowRequest;
import com.group8.library_management.entity.BorrowRequestDetail;
import com.group8.library_management.entity.User;
import com.group8.library_management.enums.BorrowRecordStatus;
import com.group8.library_management.enums.BorrowRequestDetailStatus;
import com.group8.library_management.enums.BorrowRequestStatus;
import com.group8.library_management.exception.ResourceNotFoundException;
import com.group8.library_management.repository.BorrowRecordRepository;
import com.group8.library_management.repository.BorrowRequestDetailRepository;
import com.group8.library_management.repository.BorrowRequestRepository;
import com.group8.library_management.repository.UserRepository;
import com.group8.library_management.service.UserService;
import com.group8.library_management.utils.GetMessage;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BorrowRecordRepository borrowRecordRepository;
    private final BorrowRequestRepository borrowRequestRepository;
    private final BorrowRequestDetailRepository borrowRequestDetailRepository;
    private final GetMessage getMessage;

    public UserServiceImpl(UserRepository userRepository, BorrowRecordRepository borrowRecordRepository, BorrowRequestRepository borrowRequestRepository, BorrowRequestDetailRepository borrowRequestDetailRepository, GetMessage getMessage) {
        this.userRepository = userRepository;
        this.borrowRecordRepository = borrowRecordRepository;
        this.borrowRequestRepository = borrowRequestRepository;
        this.borrowRequestDetailRepository = borrowRequestDetailRepository;
        this.getMessage = getMessage;
    }

    @Override
    public Page<User> getAllUsers(int page, int size, String searchName, String sortBy) {
        Sort sort = Optional.ofNullable(sortBy)
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    if (s.contains(",")) {
                        String[] parts = s.split(",");
                        return Sort.by(Sort.Direction.fromString(parts[1].trim().toUpperCase()), parts[0].trim());
                    } else {
                        return Sort.by(Sort.Order.asc(s.trim()));
                    }
                })
                .orElse(Sort.by(Sort.Order.asc("id")));

        Pageable pageable = PageRequest.of(page, size, sort);

        if (searchName != null && !searchName.trim().isEmpty()) {
            return userRepository.findByNameContainsIgnoreCase(searchName.trim(), pageable);
        }
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public void deactivateUser(Integer id) {
        Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
        logger.info("Starting deactivation for user ID: {}", id);

        User deactivatedUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getMessage.msg("user.error.notFound")));
        logger.info("User found: {}", deactivatedUser.getUsername());

        boolean hasBorrowedBooks = borrowRecordRepository.existsByUserIdAndStatus(id, List.of(BorrowRecordStatus.BORROWED, BorrowRecordStatus.OVERDUE));
        logger.info("Checked borrowed books: {}", hasBorrowedBooks);
        if (hasBorrowedBooks) {
            throw new IllegalArgumentException(getMessage.msg("user.deactivate.error.borrowed"));
        }

        try {
            List<BorrowRequest> activePendingRequests = borrowRequestRepository.findByUserIdAndStatusIn(
                    deactivatedUser.getId(), List.of(BorrowRequestStatus.PENDING, BorrowRequestStatus.APPROVED, BorrowRequestStatus.PARTIALLY_APPROVED));
            logger.info("Found {} active borrow requests", activePendingRequests.size());

            for (BorrowRequest request : activePendingRequests) {
                logger.info("Processing borrow request ID: {}", request.getId());
                request.setStatus(BorrowRequestStatus.CANCELLED);
                request.setRejectReason(getMessage.msg("user.deactivate.reason"));
                borrowRequestRepository.save(request);

                List<BorrowRequestDetail> details = borrowRequestDetailRepository.findByBorrowRequestId(request.getId());
                logger.info("Found {} borrow request details for request ID: {}", details.size(), request.getId());
                for (BorrowRequestDetail detail : details) {
                    if (detail.getStatus().equals(BorrowRequestDetailStatus.PENDING) || detail.getStatus().equals(BorrowRequestDetailStatus.APPROVED)) {
                        logger.info("Updating borrow request detail ID: {}", detail.getId());
                        detail.setStatus(BorrowRequestDetailStatus.REJECTED);
                        detail.setRejectReason(getMessage.msg("user.deactivate.error.borrowed"));
                        borrowRequestDetailRepository.save(detail);
                    }
                }
            }
            deactivatedUser.setActivationDate(null);
            userRepository.save(deactivatedUser);
            logger.info("User deactivated successfully");
        } catch (Exception e) {
            logger.error("Failed to deactivate user ID: {}. Error: {}", id, e.getMessage(), e);
            throw new IllegalStateException("Failed to deactivate user: " + e.getMessage(), e);
        }
    }

    @Override
    public void reactivateUser(Integer id) {
        User reactivatedUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                getMessage.msg("user.error.notFound")
        ));
        reactivatedUser.setActivationDate(LocalDateTime.now());
        userRepository.save(reactivatedUser);
    }

}
