package com.group8.library_management.mapper;
import com.group8.library_management.dto.request.RegisterUserRequestDto;
import com.group8.library_management.entity.User;
import com.group8.library_management.enums.Role;

public class UserMapper {
    public static User toEntity(RegisterUserRequestDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .role(Role.USER)
                .build();
    }
}
