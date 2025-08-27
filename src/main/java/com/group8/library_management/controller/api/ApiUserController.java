package com.group8.library_management.controller.api;

import com.group8.library_management.dto.response.BaseAPIRes;
import com.group8.library_management.dto.response.UserProfileRes;
import com.group8.library_management.service.UserService;
import com.group8.library_management.utils.GetMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api/${api.version}/user")
@RequiredArgsConstructor
public class ApiUserController {
    private final UserService userService;
    private final GetMessage getMessage;

    @GetMapping("/profile")
    public ResponseEntity<BaseAPIRes<?>> getUserProfile() {
        UserProfileRes profile = userService.getUserProfile();
        return ResponseEntity.ok(
                BaseAPIRes.success(
                        getMessage.msg("user.profile.fetched", null, Locale.getDefault()),
                        profile
                )
        );
    }
}
