package com.group8.library_management.service.impl;

import com.group8.library_management.repository.UserRepository;
import com.group8.library_management.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
