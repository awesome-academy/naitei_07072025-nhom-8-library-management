package com.group8.library_management.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequestDto {

    @NotBlank(message = "{username.notblank}")
    @Size(min = 3, max = 20, message = "{username.size}")
    @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "{username.pattern}")
    private String username;

    @NotBlank(message = "{email.notblank}")
    @Email(message = "{email.invalid}")
    private String email;

    @NotBlank(message = "{password.notblank}")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
            message = "{password.pattern}"
    )
    private String password;

    @NotBlank(message = "{fullname.notblank}")
    private String fullName;

    @NotBlank(message = "{phone.notblank}")
    @Pattern(
            regexp = "^(03|05|07|08|09)\\d{8}$",
            message = "{phone.pattern}"
    )
    private String phone;
}
