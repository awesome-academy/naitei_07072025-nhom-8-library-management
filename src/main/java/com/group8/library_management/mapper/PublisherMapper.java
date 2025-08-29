package com.group8.library_management.mapper;

import com.group8.library_management.dto.response.BookSimpleDto;
import com.group8.library_management.dto.response.PublisherDetailResponseDto;
import com.group8.library_management.entity.Publisher;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class PublisherMapper {
    public PublisherDetailResponseDto toDetailDto(Publisher publisher) {
        List<BookSimpleDto> books = publisher.getBooks() == null ? List.of() :
                publisher.getBooks().stream()
                        .map(AuthorMapper::toBookSimpleDto)
                        .collect(Collectors.toList());
        return PublisherDetailResponseDto.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .address(publisher.getAddress())
                .logo(publisher.getLogo())
                .createdAt(publisher.getCreatedAt())
                .books(books)
                .build();
    }
}
