package com.group8.library_management.service;


import com.group8.library_management.dto.response.AuthorDetailResponseDto;

import java.util.List;

public interface AuthorService {
    AuthorDetailResponseDto getAuthorDetail(Integer id);
}
