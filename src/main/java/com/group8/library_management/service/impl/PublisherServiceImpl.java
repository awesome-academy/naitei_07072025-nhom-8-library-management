package com.group8.library_management.service.impl;

import com.group8.library_management.dto.response.PublisherDetailResponseDto;
import com.group8.library_management.entity.Publisher;
import com.group8.library_management.exception.ResourceNotFoundException;
import com.group8.library_management.mapper.PublisherMapper;
import com.group8.library_management.repository.PublisherRepository;
import com.group8.library_management.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublisherServiceImpl implements PublisherService {

    final PublisherRepository publisherRepository;
    final MessageSource messageSource;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository, MessageSource messageSource) {
        this.publisherRepository = publisherRepository;
        this.messageSource = messageSource;
    }

    private String msg(String key, Object... args) {
        return messageSource.getMessage(key, args, key, LocaleContextHolder.getLocale());
    }

    @Override
    public PublisherDetailResponseDto getPublisherDetail(Integer id) {
        Optional<Publisher> publisherOptional = publisherRepository.findById(id);
        if (publisherOptional.isEmpty()) {
            throw new ResourceNotFoundException(msg("publisher.not.found", id));
        }
        return PublisherMapper.toDetailDto(publisherOptional.get());
    }
}
