package com.group8.library_management.repository;

import com.group8.library_management.dto.response.DueSoonBookRes;
import com.group8.library_management.entity.BorrowRecord;
import com.group8.library_management.enums.BorrowRecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Integer> {
    long countByStatusAndDueDate(BorrowRecordStatus status, LocalDate dueDate);
    long countByStatus(BorrowRecordStatus status);

    @Query("""
        SELECT new com.group8.library_management.dto.response.DueSoonBookRes(
            br.id,
            u.fullName,
            b.title,
            br.dueDate,
            br.returnedAt,
            c.id,
            CASE WHEN br.dueDate < :currentDate THEN true ELSE false END,
            DATEDIFF(br.dueDate, :currentDate)
        )
        FROM BorrowRecord br
        JOIN br.borrowRequestDetail brd
        JOIN brd.borrowRequest r
        JOIN r.user u
        JOIN brd.copy c
        JOIN c.book b
        WHERE br.returnedAt IS NULL 
        AND br.dueDate <= :limitDate 
        AND br.dueDate >= :currentDate
        """)
    List<DueSoonBookRes> findDueSoonBooks(@Param("currentDate") LocalDate currentDate, @Param("limitDate") LocalDate limitDate);
}
