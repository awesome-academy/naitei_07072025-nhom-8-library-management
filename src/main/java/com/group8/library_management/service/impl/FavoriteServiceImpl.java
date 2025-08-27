package com.group8.library_management.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.group8.library_management.entity.Favorite;
import com.group8.library_management.entity.FavoriteId;
import com.group8.library_management.repository.FavoriteRepository;
import com.group8.library_management.service.FavoriteService;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Override
    public boolean removeFavorite(Integer userId, Integer bookId) {
        FavoriteId favoriteId = new FavoriteId(userId, bookId);
        Favorite favorite = favoriteRepository.findById(favoriteId).orElse(null);
        if (favorite != null) {
            favoriteRepository.delete(favorite);
            return true;
        }
        return false;
    }
}
