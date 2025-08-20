package com.group8.library_management.config;

import com.group8.library_management.exception.AuthenticationFailedException;
import com.group8.library_management.service.JwtService;
import com.group8.library_management.service.TokenBlacklistService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Locale;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenBlacklistService tokenBlacklistService;
    private final MessageSource messageSource;

    @Value("${api.version}")
    private String apiVersion;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService,
            TokenBlacklistService tokenBlacklistService,
            MessageSource messageSource
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.tokenBlacklistService = tokenBlacklistService;
        this.messageSource = messageSource;
    }

    // Method lấy message theo locale
    private String getMessage(String key, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, args, locale);
    }

    // Method lấy token từ header Authorization
    private String extractJwtFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    // Method kiểm tra chưa có Authentication trong SecurityContext hay đã hết hạn
    private void authenticateUser(String jwt, HttpServletRequest request) throws AuthenticationFailedException {
        final String username = jwtService.extractUsername(jwt);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (username == null) {
            String message = getMessage("auth.token.no_username");
            logger.warn(message);
            throw new AuthenticationFailedException(message);
        }

        if (authentication == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.debug(getMessage("auth.token.validated", username));
            } else {
                String message = getMessage("auth.token.invalid_or_expired");
                logger.warn("{} User: {}", message, username);
                throw new AuthenticationFailedException(message);
            }
        }
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String path = request.getRequestURI();
        logger.debug("Processing request for path: {}", path);
        String apiPrefix = "/api/" + apiVersion + "/auth/";
        // Bỏ qua các endpoint auth
        if (path.startsWith(apiPrefix)
                || path.startsWith("/admin/")
                || path.startsWith("/css/")
                || path.startsWith("/js/")
                || path.startsWith("/images/")
                || path.startsWith("/vendor/")
                || path.startsWith("/webfonts/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 1. Lấy token từ header
        final String jwt = extractJwtFromRequest(request);
        if (jwt == null) {
            String message = getMessage("auth.header.missing");
            logger.warn("{} Path: {}", message, path);
            throw new AuthenticationFailedException(message);
        }

        // 2. Kiểm tra token có trong blacklist không
        if (tokenBlacklistService.isTokenBlacklisted(jwt)) {
            String message = getMessage("auth.token.blacklisted");
            logger.warn("{} Path: {}", message, path);
            throw new AuthenticationFailedException(message);
        }
        // 3. Kiểm tra và xác thực người dùng
        authenticateUser(jwt, request);
        logger.debug(getMessage("auth.token.validated", jwtService.extractUsername(jwt)));

        // 4. Tiếp tục chuỗi filter
        filterChain.doFilter(request, response);
    }
}
