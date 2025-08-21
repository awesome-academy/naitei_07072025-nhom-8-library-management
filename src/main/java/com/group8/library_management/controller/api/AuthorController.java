package com.group8.library_management.controller.api;

import com.group8.library_management.dto.response.AuthorDetailResponseDto;
import com.group8.library_management.dto.response.BaseAPIRes;
import com.group8.library_management.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/${api.version}/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/{id}")
    public ResponseEntity<BaseAPIRes<AuthorDetailResponseDto>> getAuthor(
            @PathVariable Integer id,
            Locale locale) {
        AuthorDetailResponseDto author = authorService.getAuthorDetail(id);
        String message = messageSource.getMessage("author.detail.fetched", null, locale);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(BaseAPIRes.success(HttpStatus.OK.value(), message, author));
    }
}
