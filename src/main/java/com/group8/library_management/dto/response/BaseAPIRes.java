package com.group8.library_management.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseAPIRes<T> {
    private int code;      // HTTP status code: 200, 400, 401, ...
    private String message;
    private T response;


    /**
     * Trả về thành công với code tự truyền
     * @param code    Mã HTTP
     * @param message Thông điệp trả về
     * @param response Dữ liệu trả về
     */
    public static <T> BaseAPIRes<T> success(int code, String message, T response) {
        return BaseAPIRes.<T>builder()
                .code(code)
                .message(message)
                .response(response)
                .build();
    }

    /**
     * Trả về thành công với HttpStatus của Spring
     */
    public static <T> BaseAPIRes<T> success(HttpStatus status, String message, T response) {
        return success(status.value(), message, response);
    }

    /**
     * Trả về thành công mặc định code = 200 OK
     */
    public static <T> BaseAPIRes<T> success(String message, T response) {
        return success(HttpStatus.OK, message, response);
    }

    /**
     * Trả về lỗi với code tự truyền
     * @param code    Mã HTTP
     * @param message Thông điệp lỗi
     */
    public static <T> BaseAPIRes<T> error(int code, String message) {
        return BaseAPIRes.<T>builder()
                .code(code)
                .message(message)
                .response(null)
                .build();
    }

    /**
     * Trả về lỗi với code + message + chi tiết response
     * @param code Mã HTTP
     * @param message Thông điệp lỗi
     * @param response Dữ liệu trả về
     */
    public static <T> BaseAPIRes<T> error(int code, String message, T response) {
        return BaseAPIRes.<T>builder()
                .code(code)
                .message(message)
                .response(response)
                .build();
    }

    /**
     * Trả về lỗi với HttpStatus của Spring
     */
    public static <T> BaseAPIRes<T> error(HttpStatus status, String message) {
        return error(status.value(), message);
    }

    /**
     * Trả về lỗi mặc định code = 500 INTERNAL_SERVER_ERROR
     */
    public static <T> BaseAPIRes<T> error(String message) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
