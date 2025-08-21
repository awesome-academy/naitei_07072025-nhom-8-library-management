package com.group8.library_management.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookSimpleDto {
    private Integer id;
    private String title;
}
