package com.group8.library_management.service;


import com.group8.library_management.dto.request.RegisterUserRequestDto;
import com.group8.library_management.entity.User;
import com.group8.library_management.exception.DuplicateResourceException;
import com.group8.library_management.mapper.UserMapper;
import com.group8.library_management.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserRequestDto input) {
        if (userRepository.existsByUsername(input.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }
        if (userRepository.existsByEmail(input.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        if (userRepository.existsByPhone(input.getPhone())) {
            throw new DuplicateResourceException("Phone number already exists");
        }

        User user = UserMapper.toEntity(input);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
