package com.group8.library_management.service.impl;

import com.group8.library_management.entity.Author;
import com.group8.library_management.repository.AuthorRepository;
import com.group8.library_management.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public void createAuthor(Author author) throws IllegalArgumentException {
        Optional<Author> existingAuthor = authorRepository.findByName(author.getName());
        if (existingAuthor.isPresent()) {
            throw new IllegalArgumentException("Author with name '" + author.getName() + "' already exists");
        }
        authorRepository.save(author);
    }

}
