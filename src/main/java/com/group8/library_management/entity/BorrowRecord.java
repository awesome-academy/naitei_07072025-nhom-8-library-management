package com.group8.library_management.entity;

import com.group8.library_management.enums.BorrowRecordStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "borrow_records")
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "borrow_request_detail_id", nullable = false)
    private BorrowRequestDetail borrowRequestDetail;

    @Column(name = "borrowed_at", nullable = false)
    private LocalDate borrowedAt;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "returned_at")
    private LocalDate returnedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BorrowRecordStatus status = BorrowRecordStatus.BORROWED;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
