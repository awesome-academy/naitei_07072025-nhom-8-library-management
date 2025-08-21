package com.group8.library_management.service;

import com.group8.library_management.dto.request.LoginUserRequestDto;
import com.group8.library_management.dto.request.RegisterUserRequestDto;
import com.group8.library_management.entity.User;
import com.group8.library_management.exception.AuthenticationFailedException;
import com.group8.library_management.exception.DuplicateResourceException;
import com.group8.library_management.mapper.UserMapper;
import com.group8.library_management.repository.UserRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final MessageSource messageSource;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            MessageSource messageSource
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
    }

    // lấy message theo locale
    private String getMessage(String key, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, args, locale);
    }

    public User signup(RegisterUserRequestDto input) {
        if (userRepository.existsByUsername(input.getUsername())) {
            throw new DuplicateResourceException(getMessage("error.duplicate.resource"));
        }
        if (userRepository.existsByEmail(input.getEmail())) {
            throw new DuplicateResourceException(getMessage("error.duplicate.resource"));
        }
        if (userRepository.existsByPhone(input.getPhone())) {
            throw new DuplicateResourceException(getMessage("error.duplicate.resource"));
        }

        User user = UserMapper.toEntity(input);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User authenticate(LoginUserRequestDto input) {
        Optional<User> optionalUser = userRepository.findByUsername(input.getUsername());
        if (optionalUser.isEmpty()) {
            throw new BadCredentialsException(getMessage("error.bad.credentials"));
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(input.getPassword(), user.getPassword())) {
            throw new BadCredentialsException(getMessage("error.bad.credentials"));
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getUsername(),
                            input.getPassword()
                    )
            );
        } catch (RuntimeException e) {
            throw new AuthenticationFailedException(getMessage("error.auth.failed") + ": " + e.getMessage());
        }
        return user;
    }
}
