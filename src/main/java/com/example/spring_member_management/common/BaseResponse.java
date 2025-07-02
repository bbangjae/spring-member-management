package com.example.spring_member_management.common;

import com.example.spring_member_management.exception.BaseResponseCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseResponse<T> {
    private final boolean success;
    private final int statusCode;
    private final String statusMessage;
    private final T data;

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(
                true,
                BaseResponseCode.SUCCESS.getCode(),
                BaseResponseCode.SUCCESS.getMessage(),
                data
        );
    }

    public static <T> BaseResponse<T> failure(BaseResponseCode code) {
        return new BaseResponse<>(
                false,
                code.getCode(),
                code.getMessage(),
                null
        );
    }
}