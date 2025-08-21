package com.group8.library_management.mapper;


import com.group8.library_management.dto.response.AuthorDetailResponseDto;
import com.group8.library_management.dto.response.BookSimpleDto;
import com.group8.library_management.entity.Author;
import com.group8.library_management.entity.Book;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class AuthorMapper {
    public AuthorDetailResponseDto toDetailDto(Author author) {
        List<BookSimpleDto> books = author.getBooks() == null ? List.of() :
                author.getBooks().stream().map(AuthorMapper::toBookSimpleDto).collect(Collectors.toList());
        return AuthorDetailResponseDto.builder()
                .id(author.getId())
                .name(author.getName())
                .biography(author.getBiography())
                .avatar(author.getAvatar())
                .books(books)
                .build();
    }

    public BookSimpleDto toBookSimpleDto(Book book) {
        return BookSimpleDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .build();
    }
}
