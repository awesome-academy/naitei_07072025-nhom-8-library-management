package com.group8.library_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageRes<T> {
    private List<T> content;
    private int page;            // trang hiện tại
    private int size;            // size trang
    private long totalItems;  // tổng item
    private int totalPages;      // tổng số trang
}
