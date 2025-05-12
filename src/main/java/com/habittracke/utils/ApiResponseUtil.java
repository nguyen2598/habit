package com.habittracke.utils;

import com.habittracke.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponseUtil {
    // Private constructor to prevent instantiation
    private ApiResponseUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static <T> ResponseEntity<Object> ok(T data) {
        return ResponseEntity.status((HttpStatus.OK)).body(new ApiResponse<T>(HttpStatus.OK.toString(), "ok", data));
    }

    public static <T> ResponseEntity<Object> notFound(String message) {
        return ResponseEntity.status((HttpStatus.NOT_FOUND))
                .body(new ApiResponse<T>(
                        HttpStatus.NOT_FOUND.toString(),
                        message != null ? message : "Cannot find resources",
                        null));
    }

    public static ResponseEntity<Object> error(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(
                        HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                        message != null ? message : "Internal server error",
                        null));
    }


    public static ResponseEntity<Object> invalidated(Object errors, String message) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ApiResponse<>(HttpStatus.UNPROCESSABLE_ENTITY.toString(), message != null ? message :"Validation errors",errors));
    }
    public static ResponseEntity<Object> unauthorized( String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(HttpStatus.UNAUTHORIZED.toString(), message != null ? message :"unauthorized",null));
    }
}
