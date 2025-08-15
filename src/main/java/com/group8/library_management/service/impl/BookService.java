package com.group8.library_management.service.impl;


import com.group8.library_management.mapper.BookMapper;
import com.group8.library_management.dto.request.BookSearch;
import com.group8.library_management.dto.response.BookDetailDTO;
import com.group8.library_management.entity.Book;
import com.group8.library_management.repository.BookRepository;
import com.group8.library_management.repository.BookSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Page<BookDetailDTO> getAllBooks(int page, int size) {
        Page<Book> books = bookRepository.findAll(PageRequest.of(page, size));
        return books.map(BookMapper::toDto);
    }

    public Page<BookDetailDTO> searchBooks(BookSearch request, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Book> books = bookRepository.findAll((root, query, cb) -> {
            var predicates = cb.conjunction();

            if (request.getTitle() != null && !request.getTitle().isEmpty()) {
                predicates = cb.and(predicates,
                        root.get("title").in(request.getTitle()));
            }

            if (request.getAuthors() != null && !request.getAuthors().isEmpty()) {
                predicates = cb.and(predicates,
                        root.get("author").get("id").in(request.getAuthors()));
            }

            if (request.getPublishers() != null && !request.getPublishers().isEmpty()) {
                predicates = cb.and(predicates,
                        root.get("publisher").get("id").in(request.getPublishers()));
            }

            if (request.getGenres() != null && !request.getGenres().isEmpty()) {
                predicates = cb.and(predicates,
                        root.get("genre").get("id").in(request.getGenres()));
            }

            return predicates;
        }, pageable);

        return books.map(BookMapper::toDto);
    }

    public BookDetailDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        return BookMapper.toDto(book);
    }
}
