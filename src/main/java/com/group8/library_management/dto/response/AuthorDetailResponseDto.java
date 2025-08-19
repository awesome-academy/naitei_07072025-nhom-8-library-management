package com.group8.library_management.dto.response;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AuthorDetailResponseDto {
    private Integer id;
    private String name;
    private String biography;
    private String avatar;
    private List<BookSimpleDto> books;
}
