package com.group8.library_management.controller.api;

import com.group8.library_management.dto.request.RegisterUserRequestDto;
import com.group8.library_management.dto.response.BaseAPIRes;
import com.group8.library_management.entity.User;
import com.group8.library_management.event.OnRegistrationCompleteEvent;
import com.group8.library_management.service.AuthenticationService;
import com.group8.library_management.service.impl.VerificationServiceImpl;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RequestMapping("/auth")
@RestController
public class ApiAuthController {

    private final AuthenticationService authenticationService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final VerificationServiceImpl verificationService;
    private final MessageSource messageSource;


    public ApiAuthController(
            AuthenticationService authenticationService, ApplicationEventPublisher applicationEventPublisher, VerificationServiceImpl verificationService, MessageSource messageSource) {
        this.authenticationService = authenticationService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.verificationService = verificationService;
        this.messageSource = messageSource;
    }

    @PostMapping("/signup")
    public ResponseEntity<BaseAPIRes<User>> register(@Valid @RequestBody RegisterUserRequestDto registerUserDto,
                                                     Locale locale) {

        User registeredUser = authenticationService.signup(registerUserDto);
        applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser));

        String message = messageSource.getMessage("user.register.success", null, locale);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseAPIRes.success(HttpStatus.CREATED.value(), message, registeredUser));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<BaseAPIRes<String>> verifyEmail(@RequestParam("token") String token,
                                                          Locale locale) {
        try {
            verificationService.validateVerification(token);
            String message = messageSource.getMessage("email.verify.success", null, locale);
            return ResponseEntity.ok(BaseAPIRes.success(message, null));
        } catch (IllegalArgumentException e) {
            String message = messageSource.getMessage("email.verify.invalid", null, locale);
            return ResponseEntity.badRequest().body(BaseAPIRes.error(message));
        }
    }
}
