package com.group8.library_management.service;

import com.group8.library_management.entity.Author;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface AuthorService {
    void createAuthor(Author author) throws IllegalArgumentException;

}
