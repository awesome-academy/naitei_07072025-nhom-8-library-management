package com.group8.library_management.controller.api;

import com.group8.library_management.dto.request.RegisterUserRequestDto;
import com.group8.library_management.dto.response.BaseAPIRes;
import com.group8.library_management.entity.User;
import com.group8.library_management.service.AuthenticationService;
import com.group8.library_management.service.JwtService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/auth")
@RestController
public class ApiAuthController {

    private final AuthenticationService authenticationService;


    public ApiAuthController(JwtService jwtService,
                             AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<BaseAPIRes<User>> register(@Valid @RequestBody RegisterUserRequestDto registerUserDto) throws BadRequestException {

        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseAPIRes.success(HttpStatus.CREATED.value(),
                        "User registered successfully", registeredUser));
    }
}
