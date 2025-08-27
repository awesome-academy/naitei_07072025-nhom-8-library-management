package com.group8.library_management.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserProfileRes {
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String avatar;
}
