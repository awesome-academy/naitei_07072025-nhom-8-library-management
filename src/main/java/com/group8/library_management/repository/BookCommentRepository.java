package com.group8.library_management.repository;

import com.group8.library_management.entity.BookComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookCommentRepository extends JpaRepository<BookComment, Integer> {

}

