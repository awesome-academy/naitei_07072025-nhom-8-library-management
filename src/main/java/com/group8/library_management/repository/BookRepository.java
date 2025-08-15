package com.group8.library_management.repository;

import com.group8.library_management.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @EntityGraph(attributePaths = {"author", "publisher", "genre"})
    Page<Book> findAll(Pageable pageable);

    @Query("SELECT b FROM Book b " +
            "JOIN b.author a " +
            "JOIN b.publisher p " +
            "JOIN b.genre g " +
            "WHERE (:titles IS NULL OR b.title IN :titles) " +
            "AND (:authors IS NULL OR a.name IN :authors) " +
            "AND (:publishers IS NULL OR p.name IN :publishers) " +
            "AND (:genres IS NULL OR g.name IN :genres)")
    Page<Book> searchBooks(List<String> titles,
                           List<String> authors,
                           List<String> publishers,
                           List<String> genres,
                           Pageable pageable);
}