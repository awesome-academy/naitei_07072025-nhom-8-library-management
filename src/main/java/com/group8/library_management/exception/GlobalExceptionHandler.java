package com.group8.library_management.exception;

import com.group8.library_management.dto.response.BaseAPIRes;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }

    /**
     * Handles lỗi xác thực.
     *
     * @param ex ngoại lệ được ném ra khi xác thực thất bại
     * @return ResponseEntity hoặc redirect MVC với thông điệp lỗi
     */
    @ExceptionHandler(AuthenticationFailedException.class)
    public Object handleAuthFailed(AuthenticationFailedException ex, HttpServletRequest request) {
        logger.error("Authentication failed: {}", ex.getMessage());
        String message = ex.getMessage() != null ? ex.getMessage() : getMessage("error.auth.failed");

        if (isApiRequest(request)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(BaseAPIRes.error(HttpStatus.UNAUTHORIZED.value(), message));
        }
        return redirectBackWithError(request, message);
    }

    /**
     * Handles lỗi yêu cầu không hợp lệ (IllegalArgumentException).
     *
     * @param ex ngoại lệ được ném ra khi yêu cầu không hợp lệ
     * @return ResponseEntity hoặc redirect MVC với thông điệp lỗi
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Object handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        logger.error("Invalid argument: {}", ex.getMessage());
        String message = ex.getMessage() != null ? ex.getMessage() : getMessage("error.invalid.request");

        if (isApiRequest(request)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(BaseAPIRes.error(HttpStatus.BAD_REQUEST.value(), message));
        }

        return redirectBackWithError(request, message);
    }


    /**
     * Handles các lỗi khác trong ứng dụng.
     *
     * @param ex ngoại lệ chung được ném ra khi xảy ra lỗi không xác định
     * @return ResponseEntity hoặc redirect MVC với thông điệp lỗi
     */
    @ExceptionHandler(Exception.class)
    public Object handleGenericException(Exception ex, HttpServletRequest request) {
        logger.error("Unexpected error: {}", ex.getMessage(), ex);
        String message = getMessage("error.unexpected");

        if (isApiRequest(request)) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseAPIRes.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), message));
        }
        return redirectBackWithError(request, message);
    }

    /**
     * Handles lỗi khi đưa vào dữ liệu bị trùng lặp.
     *
     * @param ex ngoại lệ được ném ra khi dữ liệu bị trùng lặp trong database
     * @return ResponseEntity hoặc redirect MVC với thông điệp lỗi
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public Object handleDuplicateResource(DuplicateResourceException ex, HttpServletRequest request) {
        logger.error("Duplicate resource: {}", ex.getMessage());
        String message = ex.getMessage() != null ? ex.getMessage() : getMessage("error.duplicate.resource");

        if (isApiRequest(request)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(BaseAPIRes.error(HttpStatus.CONFLICT.value(), message));
        }
        return redirectBackWithError(request, message);
    }

    /**
     * Handles lỗi yêu cầu không hợp lệ từ client.
     *
     * @param ex ngoại lệ được ném ra khi yêu cầu của client không hợp lệ
     * @return ResponseEntity hoặc redirect MVC với thông điệp lỗi
     */
    @ExceptionHandler(BadRequestException.class)
    public Object handleBadRequest(BadRequestException ex, HttpServletRequest request) {
        logger.error("Bad request: {}", ex.getMessage());
        String message = ex.getMessage() != null ? ex.getMessage() : getMessage("error.invalid.request");

        if (isApiRequest(request)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(BaseAPIRes.error(HttpStatus.BAD_REQUEST.value(), message));
        }
        return redirectBackWithError(request, message);
    }

    /**
     * Handles lỗi từ chối truy cập do thiếu quyền.
     *
     * @param ex ngoại lệ được ném ra khi người dùng không có quyền thực hiện hành động
     * @return ResponseEntity hoặc redirect MVC với thông điệp lỗi
     */
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public Object handleAccessDenied(org.springframework.security.access.AccessDeniedException ex, HttpServletRequest request) {
        logger.error("Access denied: {}", ex.getMessage());
        String message = getMessage("error.access.denied");

        if (isApiRequest(request)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(BaseAPIRes.error(HttpStatus.FORBIDDEN.value(), message));
        }
        return redirectBackWithError(request, message);
    }

    /**
     * Handles lỗi xác thực không thành công do thông tin đăng nhập không hợp lệ.
     *
     * @param ex ngoại lệ được ném ra khi thông tin đăng nhập không hợp lệ
     * @return ResponseEntity hoặc redirect MVC với thông điệp lỗi
     */
    @ExceptionHandler(BadCredentialsException.class)
    public Object handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        logger.error("Bad credentials: {}", ex.getMessage());
        String message = ex.getMessage() != null ? ex.getMessage() : getMessage("error.bad.credentials");

        if (isApiRequest(request)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(BaseAPIRes.error(HttpStatus.UNAUTHORIZED.value(), message));
        }
        return redirectBackWithError(request, message);
    }

    /**
     * Handles lỗi validation (Bean Validation trong DTO).
     *
     * @param ex MethodArgumentNotValidException
     * @return ResponseEntity hoặc redirect MVC với thông điệp lỗi và chi tiết field
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        logger.warn("Validation failed: {}", errors);
        String message = getMessage("error.validation.failed");

        if (isApiRequest(request)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(BaseAPIRes.error(HttpStatus.BAD_REQUEST.value(), message, errors));
        }
        ModelAndView modelAndView = redirectBackWithError(request, message);
        modelAndView.addObject("fieldErrors", errors);
        return modelAndView;
    }

    /**
     * Handles lỗi không tìm thấy tài nguyên (ví dụ khi id không tồn tại).
     *
     * @param ex ngoại lệ ResourceNotFoundException
     * @return ResponseEntity hoặc redirect MVC với thông điệp lỗi
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public Object handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        logger.error("Resource not found: {}", ex.getMessage());
        String message = ex.getMessage() != null ? ex.getMessage() : getMessage("error.not.found");

        if (isApiRequest(request)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(BaseAPIRes.error(HttpStatus.NOT_FOUND.value(), message));
        }
        return redirectBackWithError(request, message);
    }

    /**
     * Handles lỗi khi không tìm thấy endpoint (NoHandlerFoundException).
     *
     * @param ex NoHandlerFoundException
     * @return ResponseEntity hoặc redirect MVC với thông điệp lỗi
     */
    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    public Object handleNoHandlerFound(org.springframework.web.servlet.NoHandlerFoundException ex, HttpServletRequest request) {
        logger.error("No handler found for request: {} {}", ex.getHttpMethod(), ex.getRequestURL());
        String message = getMessage("error.endpoint.not.found");

        if (isApiRequest(request)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(BaseAPIRes.error(HttpStatus.NOT_FOUND.value(), message));
        }
        return redirectBackWithError(request, message);
    }


    /**
     * Chuyển hướng về trang trước đó với thông điệp lỗi (dành cho MVC của admin).
     * @param request HttpServletRequest
     * @param message tin nhắn báo lỗi
     * @return modalAndView để chuyển hướng
     */
    private ModelAndView redirectBackWithError(HttpServletRequest request, String message) {
        String referer = request.getHeader("Referer");
        if (referer == null || referer.isEmpty()) {
            referer = "/admin/dashboard";
        }
        logger.info("Redirecting to: {}, with error message: {}", referer, message); // log để debug
        ModelAndView modelAndView = new ModelAndView("redirect:" + referer);

        RequestContextUtils.getOutputFlashMap(request).put("errorMessage", message);
        return modelAndView;
    }

    /**
     * Kiểm tra xem request có phải API không (theo URL prefix /api/)
     */
    private boolean isApiRequest(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/api/");
    }
}
