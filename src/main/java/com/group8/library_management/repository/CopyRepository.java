package com.group8.library_management.repository;

import com.group8.library_management.entity.Copy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CopyRepository extends JpaRepository<Copy, Integer> {

    @Query(value = """
        SELECT c.* FROM copies c
        WHERE c.book_id = :bookId
          AND c.id NOT IN (
              SELECT bu.copy_id FROM book_unavailabilities bu
              WHERE bu.copy_id = c.id
          )
          AND c.id NOT IN (
              SELECT brd.copy_id FROM borrow_request_details brd
              JOIN borrow_requests br ON brd.borrow_request_id = br.id
              WHERE brd.copy_id = c.id
                AND br.status IN ('pending', 'approved', 'partially_approved')
          )
        LIMIT 1
    """, nativeQuery = true)
    Copy findAvailableCopyByBookId(@Param("bookId") Integer bookId);

    @Query(value = """
        SELECT COUNT(*) FROM copies c
        WHERE c.book_id = :bookId
          AND c.id NOT IN (
              SELECT bu.copy_id FROM book_unavailabilities bu
              WHERE bu.copy_id = c.id
          )
          AND c.id NOT IN (
              SELECT brd.copy_id FROM borrow_request_details brd
              JOIN borrow_requests br ON brd.borrow_request_id = br.id
              WHERE brd.copy_id = c.id
                AND br.status IN ('pending', 'approved', 'partially_approved')
          )
    """, nativeQuery = true)
    int countAvailableCopies(@Param("bookId") Integer bookId);
}
