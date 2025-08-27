package com.group8.library_management.service;

import com.group8.library_management.enums.EntityType;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String uploadImage(String entityType, Integer entityId, MultipartFile file) throws Exception;
    String getCoverImage(EntityType entityType, Integer entityId);
    void removeImage(EntityType entityType, Integer entityId) throws Exception;
    boolean isValidImageFile(MultipartFile file);
}
