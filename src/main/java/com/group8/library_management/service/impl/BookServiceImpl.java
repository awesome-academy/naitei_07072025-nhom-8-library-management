package com.group8.library_management.service.impl;

import com.group8.library_management.dto.response.PageRes;
import com.group8.library_management.dto.request.BookSearchReq;
import com.group8.library_management.dto.response.*;
import com.group8.library_management.entity.Book;
import com.group8.library_management.mapper.BookMapper;
import com.group8.library_management.repository.BookRepository;
import com.group8.library_management.service.BookService;
import com.group8.library_management.specification.BookSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public PageRes<BookDetailRes> searchBooks(BookSearchReq req, int page, int size) {
        // Pageable (thêm sort mặc định theo title)
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());

        // Build spec
        var spec = BookSpecification.build(req.getTitle(), req.getAuthors(), req.getPublishers(), req.getGenres());

        // Query
        Page<Book> bookPage = bookRepository.findAll(spec, pageable);

        // Map content
        List<BookDetailRes> items = bookPage.getContent()
                .stream()
                .map(BookMapper::toDto)
                .toList();

        // Gói vào PageResult
        return new PageRes<>(
                items,
                bookPage.getNumber(),
                bookPage.getSize(),
                bookPage.getTotalElements(),
                bookPage.getTotalPages()
        );
    }
}
