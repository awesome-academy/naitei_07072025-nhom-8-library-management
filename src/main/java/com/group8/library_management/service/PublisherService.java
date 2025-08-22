package com.group8.library_management.service;

import com.group8.library_management.dto.response.PublisherDetailResponseDto;

public interface PublisherService {
    PublisherDetailResponseDto getPublisherDetail(Integer id);
}
