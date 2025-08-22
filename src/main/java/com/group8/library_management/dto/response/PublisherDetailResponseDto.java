package com.group8.library_management.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class PublisherDetailResponseDto {
    private Integer id;
    private String name;
    private String address;
    private String logo;
    private LocalDateTime createdAt;
    private List<BookSimpleDto> books;
}
