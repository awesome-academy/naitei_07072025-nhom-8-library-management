package com.group8.library_management.service.impl;

import com.group8.library_management.dto.response.AuthorDetailResponseDto;
import com.group8.library_management.entity.Author;
import com.group8.library_management.exception.ResourceNotFoundException;
import com.group8.library_management.mapper.AuthorMapper;
import com.group8.library_management.repository.AuthorRepository;
import com.group8.library_management.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    final AuthorRepository authorRepository;
    final MessageSource messageSource;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, MessageSource messageSource) {
        this.authorRepository = authorRepository;
        this.messageSource = messageSource;
    }
    private String msg(String key, Object... args) {
        return messageSource.getMessage(key, args, key, LocaleContextHolder.getLocale());
    }
    @Override
    public AuthorDetailResponseDto getAuthorDetail(Integer id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isEmpty()) {
            throw new ResourceNotFoundException(msg("author.not.found", id));
        }
        return AuthorMapper.toDetailDto(authorOptional.get());
    }
}
