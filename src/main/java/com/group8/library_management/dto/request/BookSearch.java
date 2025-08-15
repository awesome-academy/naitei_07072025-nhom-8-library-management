package com.group8.library_management.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class BookSearch{
    private List<String> title;
    private List<Long> authors;
    private List<Long> publishers;
    private List<Long> genres;
}
