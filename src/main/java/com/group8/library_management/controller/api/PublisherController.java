package com.group8.library_management.controller.api;

import com.group8.library_management.dto.response.PublisherDetailResponseDto;
import com.group8.library_management.dto.response.BaseAPIRes;
import com.group8.library_management.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/${api.version}/publishers")
public class PublisherController {
    @Autowired
    private PublisherService publisherService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/{id}")
    public ResponseEntity<BaseAPIRes<PublisherDetailResponseDto>> getPublisher(
            @PathVariable Integer id,
            Locale locale) {
        PublisherDetailResponseDto publisher = publisherService.getPublisherDetail(id);
        String message = messageSource.getMessage("publisher.detail.fetched", null, locale);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(BaseAPIRes.success(HttpStatus.OK.value(), message, publisher));
    }
}
