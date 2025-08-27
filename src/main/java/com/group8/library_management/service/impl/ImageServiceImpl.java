package com.group8.library_management.service.impl;

import com.group8.library_management.entity.Image;
import com.group8.library_management.enums.EntityType;
import com.group8.library_management.repository.ImageRepository;
import com.group8.library_management.service.ImageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.verification.url}")
    private String baseUrl;

    private final List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png");

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    @Transactional
    public String uploadImage(String entityType, Integer entityId, MultipartFile file) throws Exception {
        EntityType type = EntityType.valueOf(entityType.toUpperCase());
        System.out.println("Upload directory: " + uploadDir);
        if (!isValidImageFile(file)) {
            throw new IllegalArgumentException("Invalid file type or size");
        }

        // tạo thư mục lưu ảnh trong trường hợp chưa có thư mục
        Path uploadPath = Paths.get(uploadDir, type.getDirectory());
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // tạo tên file mới
        String extension = getFileExtension(file.getOriginalFilename());
        String filename = type.getDirectory() + "_" + entityId + "_" + UUID.randomUUID() + "." + extension;

        // lưu file vật lý
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // xóa ảnh cũ nếu có
        removeImage(type, entityId);

        // lưu thông tin vào DB
        Image image = new Image();
        image.setEntityType(type);
        image.setEntityId(entityId);
        image.setImage(type.getDirectory() + "/" + filename);
        image.setIsCover(true);
        imageRepository.save(image);

        return baseUrl + "/images/" + type.getDirectory() + "/" + filename;
    }

    @Override
    public String getCoverImage(EntityType entityType, Integer entityId) {
        Optional<Image> image = imageRepository.findByEntityTypeAndEntityIdAndIsCover(entityType, entityId, true);
        return image.map(img -> baseUrl + "/images/" + img.getImage()).orElse(null);
    }

    @Override
    public void removeImage(EntityType entityType, Integer entityId) {
        Optional<Image> existingImage = imageRepository.findByEntityTypeAndEntityIdAndIsCover(entityType, entityId, true);
        if (existingImage.isPresent()) {
            Image img = existingImage.get();
            Path filePath = Paths.get(uploadDir, img.getImage());
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                System.err.println("Failed to delete file: " + e.getMessage());
            }
            imageRepository.delete(img);
        }
    }

    @Override
    public boolean isValidImageFile(MultipartFile file) {
        // 5MB
        long maxFileSize = 5 * 1024 * 1024;
        if (file == null || file.isEmpty() || file.getSize() > maxFileSize) return false;

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) return false;

        String filename = file.getOriginalFilename();
        if (filename == null) return false;

        String extension = getFileExtension(filename).toLowerCase();
        return allowedExtensions.contains(extension);
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) return "";
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
