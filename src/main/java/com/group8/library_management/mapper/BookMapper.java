package com.group8.library_management.mapper;
import com.group8.library_management.dto.response.BookDetailDTO;
import com.group8.library_management.entity.Book;

public class BookMapper {
    public static BookDetailDTO toDto(Book book) {
        return new BookDetailDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getName(),
                book.getPublisher().getName(),
                book.getGenre().getName()
        );
    }
}
