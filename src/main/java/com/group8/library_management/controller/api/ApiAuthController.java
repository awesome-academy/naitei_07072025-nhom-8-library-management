package com.group8.library_management.controller.api;

import com.group8.library_management.dto.request.LoginUserRequestDto;
import com.group8.library_management.dto.request.RegisterUserRequestDto;
import com.group8.library_management.dto.response.BaseAPIRes;
import com.group8.library_management.dto.response.LoginRes;
import com.group8.library_management.entity.User;
import com.group8.library_management.event.OnRegistrationCompleteEvent;
import com.group8.library_management.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

@RestController
@RequestMapping("/api/${api.version}/auth")
public class ApiAuthController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final TokenBlacklistService tokenBlacklistService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final VerificationService verificationService;
    private final MessageSource messageSource;

    public ApiAuthController(JwtService jwtService,
                             AuthenticationService authenticationService,
                             TokenBlacklistService tokenBlacklistService,
                             ApplicationEventPublisher applicationEventPublisher,
                             VerificationService verificationService,
                             MessageSource messageSource) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.tokenBlacklistService = tokenBlacklistService;
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

    @PostMapping("/login")
    public ResponseEntity<BaseAPIRes<LoginRes>> authenticate(@RequestBody LoginUserRequestDto loginUserDto,
                                                             Locale locale) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginRes response = new LoginRes()
                .setToken(jwtToken)
                .setExpiresIn(jwtService.getExpirationTime());

        String message = messageSource.getMessage("user.login.success", null, locale);
        return ResponseEntity.ok(BaseAPIRes.success(HttpStatus.OK.value(), message, response));
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseAPIRes<Void>> logout(HttpServletRequest request,
                                                   Locale locale) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            String message = messageSource.getMessage("auth.header.missing", null, locale);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(BaseAPIRes.error(HttpStatus.BAD_REQUEST.value(), message));
        }

        String token = authHeader.substring(7);

        if (tokenBlacklistService.isTokenBlacklisted(token)) {
            String message = messageSource.getMessage("auth.token.blacklisted", null, locale);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(BaseAPIRes.error(HttpStatus.FORBIDDEN.value(), message));
        }

        LocalDateTime expirationTime = jwtService.extractExpiration(token)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        tokenBlacklistService.tokenBlackList(token, expirationTime);

        String message = messageSource.getMessage("user.logout.success", null, locale);
        return ResponseEntity.ok(BaseAPIRes.success(HttpStatus.OK.value(), message, null));
    }
}
