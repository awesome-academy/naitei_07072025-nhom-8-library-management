package com.group8.library_management.repository;

import com.group8.library_management.entity.Favorite;
import com.group8.library_management.entity.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {
    boolean existsById(FavoriteId id);
}
