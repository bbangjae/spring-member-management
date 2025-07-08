package com.example.spring_member_management.exception;

import lombok.Getter;

@Getter
public class MemberNotFoundException extends RuntimeException {
    private final BaseResponseCode errorCode;

    public MemberNotFoundException(BaseResponseCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}