package com.group8.library_management.service.impl;

import com.group8.library_management.dto.response.PublisherDetailResponseDto;
import com.group8.library_management.entity.Publisher;
import com.group8.library_management.exception.ResourceNotFoundException;
import com.group8.library_management.mapper.PublisherMapper;
import com.group8.library_management.repository.PublisherRepository;
import com.group8.library_management.service.PublisherService;
import com.group8.library_management.utils.GetMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository publisherRepository;
    private final GetMessage getMessage;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository, GetMessage getMessage) {
        this.publisherRepository = publisherRepository;
        this.getMessage = getMessage;
    }

    @Override
    public PublisherDetailResponseDto getPublisherDetail(Integer id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getMessage.msg("publisher.not.found", id)));
        return PublisherMapper.toDetailDto(publisher);
    }
}
