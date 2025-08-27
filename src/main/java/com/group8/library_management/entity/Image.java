package com.group8.library_management.entity;

import com.group8.library_management.enums.EntityType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntityType entityType; // "BOOK", "USER", "AUTHOR", "PUBLISHER"

    @Column(nullable = false)
    private Integer entityId;

    @Column(name = "image", nullable = false, length = 255)
    private String image;

    @Column(name = "is_cover", nullable = false)
    @ColumnDefault("false")
    private Boolean isCover = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
