package com.example.spring_member_management.exception;

import lombok.Getter;

@Getter
public class TeamNotFoundException extends RuntimeException {
    private final BaseResponseCode errorCode;

    public TeamNotFoundException(BaseResponseCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}