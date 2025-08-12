package com.group8.library_management.exception;

import com.group8.library_management.dto.response.BaseAPIRes;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles lỗi xác thực.
     *
     * @param ex ngoại lệ được ném ra khi xác thực thất bại
     * @return ResponseEntity với thông điệp lỗi và trạng thái HTTP 401 Unauthorized
     */
    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<BaseAPIRes<?>> handleAuthFailed(AuthenticationFailedException ex) {
        logger.error("Authentication failed: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(BaseAPIRes.error(HttpStatus.UNAUTHORIZED.value(), ex.getMessage() != null ? ex.getMessage() : "Authentication failed"));
    }

    /**
     * Handles các lỗi khác trong ứng dụng.
     *
     * @param ex ngoại lệ chung được ném ra khi xảy ra lỗi không xác định
     * @return ResponseEntity với thông điệp lỗi mặc định và trạng thái HTTP 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseAPIRes<?>> handleGenericException(Exception ex) {
        logger.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseAPIRes.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "An unexpected error occurred"));
    }

    /**
     * Handles lỗi yêu cầu không hợp lệ từ client.
     *
     * @param ex ngoại lệ được ném ra khi yêu cầu của client không hợp lệ
     * @return ResponseEntity với thông điệp lỗi và trạng thái HTTP 400 Bad Request
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BaseAPIRes<?>> handleBadRequest(BadRequestException ex) {
        logger.error("Bad request: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(BaseAPIRes.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    /**
     * Handles lỗi từ chối truy cập do thiếu quyền.
     *
     * @param ex ngoại lệ được ném ra khi người dùng không có quyền thực hiện hành động
     * @return ResponseEntity với thông điệp lỗi và trạng thái HTTP 403 Forbidden
     */
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<BaseAPIRes<?>> handleAccessDenied(
            org.springframework.security.access.AccessDeniedException ex) {
        logger.error("Access denied: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(BaseAPIRes.error(HttpStatus.FORBIDDEN.value(),
                        "You do not have permission to perform this action"));
    }

    /**
     * Handles lỗi xác thực không thành công do thông tin đăng nhập không hợp lệ.
     *
     * @param ex ngoại lệ được ném ra khi thông tin đăng nhập không hợp lệ
     * @return ResponseEntity với thông điệp lỗi và trạng thái HTTP 401 Unauthorized
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BaseAPIRes<?>> handleBadCredentials(BadCredentialsException ex) {
        logger.error("Bad credentials: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(BaseAPIRes.error(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()));
    }

}
