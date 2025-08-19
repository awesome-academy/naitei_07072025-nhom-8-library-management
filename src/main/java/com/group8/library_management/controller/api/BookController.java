package com.group8.library_management.controller.api;

import com.group8.library_management.constant.HttpStatusCode;
import com.group8.library_management.dto.response.PageRes;
import com.group8.library_management.dto.response.*;
import com.group8.library_management.dto.request.BookSearchReq;
import com.group8.library_management.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final MessageSource messageSource;

    @PostMapping("/search")
    public ResponseEntity<?> searchBooks(
            @RequestBody BookSearchReq request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Locale locale
    ) {
        PageRes<BookDetailRes> result = bookService.searchBooks(request, page, size);

        String message = messageSource.getMessage("book.list", null, locale);

        return ResponseEntity.ok(
                BaseAPIRes.success(HttpStatusCode.OK, message, result)
        );
    }
}
