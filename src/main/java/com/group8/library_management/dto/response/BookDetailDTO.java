package com.group8.library_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDetailDTO {
    private int id;
    private String title;
    private String author;
    private String genre;
    private String publisher;
}
