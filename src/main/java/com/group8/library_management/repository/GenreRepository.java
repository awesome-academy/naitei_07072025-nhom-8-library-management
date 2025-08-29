package com.group8.library_management.repository;

import com.group8.library_management.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    boolean existsByNameAndDeletedAtIsNull(String name);
    Genre findGenreByIdAndDeletedAtIsNull(Integer id);
    List<Genre> findAllByDeletedAtIsNull();
    Long countAllByDeletedAtIsNull();
}
