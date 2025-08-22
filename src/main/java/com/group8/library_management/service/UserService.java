package com.group8.library_management.service;
import com.group8.library_management.entity.User;

import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {

    // admin
    Page<User> getAllUsers(int page, int size, String searchName, String sortBy);
    Optional<User> getUserById(Integer id);
    void deactivateUser(Integer id);
    void reactivateUser(Integer id);
}
