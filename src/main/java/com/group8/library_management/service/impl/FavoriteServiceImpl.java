package com.group8.library_management.service.impl;

import com.group8.library_management.dto.request.AddFavoriteReq;
import com.group8.library_management.entity.Book;
import com.group8.library_management.entity.Favorite;
import com.group8.library_management.entity.FavoriteId;
import com.group8.library_management.entity.User;
import com.group8.library_management.repository.BookRepository;
import com.group8.library_management.repository.FavoriteRepository;
import com.group8.library_management.repository.UserRepository;
import com.group8.library_management.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public boolean addFavorite(Integer userId, AddFavoriteReq request) {
        if (userId == null || request == null || request.getBookId() == null) {
            return false;
        }
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Book book = bookRepository.findById(request.getBookId())
            .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        FavoriteId favoriteId = new FavoriteId();
        favoriteId.setUserId(userId);
        favoriteId.setBookId(book.getId());
        if (favoriteRepository.existsById(favoriteId)) {
            return false;
        }
        Favorite favorite = new Favorite();
        favorite.setId(favoriteId);
        favorite.setUser(user);
        favorite.setBook(book);
        favoriteRepository.save(favorite);
        return true;
    }
}
