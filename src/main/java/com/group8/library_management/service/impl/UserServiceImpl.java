package com.group8.library_management.service.impl;

import com.group8.library_management.entity.User;
import com.group8.library_management.exception.ResourceNotFoundException;
import com.group8.library_management.repository.UserRepository;
import com.group8.library_management.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> getAllUsers(int page, int size, String searchName, String sortBy) {
        Sort sort = Optional.ofNullable(sortBy)
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    if (s.contains(",")) {
                        String[] parts = s.split(",");
                        return Sort.by(Sort.Direction.fromString(parts[1].trim().toUpperCase()), parts[0].trim());
                    } else {
                        return Sort.by(Sort.Order.asc(s.trim()));
                    }
                })
                .orElse(Sort.by(Sort.Order.asc("id")));

        Pageable pageable = PageRequest.of(page, size, sort);

        if (searchName != null && !searchName.trim().isEmpty()) {
            return userRepository.findByNameContainsIgnoreCase(searchName.trim(), pageable);
        }
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public void deactivateUser(Integer id) {
        User deactivatedUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        deactivatedUser.setActivationDate(null);
        userRepository.save(deactivatedUser);
    }

    @Override
    public void reactivateUser(Integer id) {
        User reactivatedUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        reactivatedUser.setActivationDate(LocalDateTime.now());
        userRepository.save(reactivatedUser);
    }

}
