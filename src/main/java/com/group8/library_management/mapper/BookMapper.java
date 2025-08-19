package com.group8.library_management.mapper;
import com.group8.library_management.dto.response.BookDetailRes;
import com.group8.library_management.entity.Book;

public class BookMapper {
    private BookMapper() {}

    public static BookDetailRes toDto(Book b) {
        if (b == null) return null;

        return BookDetailRes.builder()
                .id(b.getId())
                .title(b.getTitle())
                .author(b.getAuthor().getName())
                .genre(b.getGenre().getName())
                .publisher(b.getPublisher().getName())
                .build();
    }
}
