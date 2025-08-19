package com.group8.library_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailRes {
    private int id;
    private String title;
    private String author;
    private String genre;
    private String publisher;
}
