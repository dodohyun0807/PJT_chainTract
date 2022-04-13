package com.ssafy.chaintract.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

// 우선 이 양식대로 해보고 안되면, 추후에 수정
public class ApiUtils {

    public static <T> ApiResult<T> success(T response) {
        return new ApiResult<>(true, response, null);
    }

    public static ApiResult<?> error(Throwable throwable, HttpStatus status) {
        return new ApiResult<>(false, null, new ApiError(throwable, status));
    }

    public static ApiResult<?> error(String message, HttpStatus status) {
        return new ApiResult<>(false, null, new ApiError(message, status));
    }

    @Getter
    @ToString
    public static class ApiError {
        private final String message;
        private final int status;

        ApiError(Throwable throwable, HttpStatus status) {
            this(throwable.getMessage(), status);
        }

        ApiError(String message, HttpStatus status) {
            this.message = message;
            this.status = status.value();
        }
    }

    @RequiredArgsConstructor
    @Getter
    @ToString
    public static class ApiResult<T> {
        private final boolean success;
        private final T response;
        private final ApiError error;

        public boolean isSuccess() {
            return success;
        }

        public ApiError getError() {
            return error;
        }

        public T getResponse() {
            return response;
        }
    }

}