package com.group8.library_management.controller;

import com.group8.library_management.dto.response.BookDetailDTO;
import com.group8.library_management.service.impl.BookService;
import com.group8.library_management.dto.request.BookSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Map;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<?> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<BookDetailDTO> books = bookService.getAllBooks(page, size);

    return ResponseEntity.ok(Map.of(
        "totalPages", books.getTotalPages(),
        "totalItems", books.getTotalElements(),
        "books", books.getContent()
    ));
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchBooks(@RequestBody BookSearch request,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        Page<BookDetailDTO> books = bookService.searchBooks(request, page, size);
        return ResponseEntity.ok(Map.of(
                "totalPages", books.getTotalPages(),
                "totalItems", books.getTotalElements(),
                "books", books.getContent()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        BookDetailDTO book = bookService.getBookById(id);
        return ResponseEntity.ok().body(
                java.util.Map.of("book", book)
        );
    }
}




