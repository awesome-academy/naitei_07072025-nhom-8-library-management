package com.group8.library_management.service;

import com.group8.library_management.dto.request.BookSearchReq;
import com.group8.library_management.dto.response.BookDetailRes;
import com.group8.library_management.dto.response.PageRes;

public interface BookService {
    public PageRes<BookDetailRes> searchBooks(BookSearchReq req, int page, int size);
    long countBooks();
}
