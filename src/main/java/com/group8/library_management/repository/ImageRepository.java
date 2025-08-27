package com.group8.library_management.repository;

import com.group8.library_management.entity.Image;
import com.group8.library_management.enums.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByEntityTypeAndEntityId(EntityType entityType, Integer entityId);
    Optional<Image> findByEntityTypeAndEntityIdAndIsCover(EntityType entityType, Integer entityId, Boolean isCover);
    void deleteByEntityTypeAndEntityId(EntityType entityType, Integer entityId);
}
