package com.example.spring_member_management.exception;

import lombok.Getter;

@Getter
public class DuplicateMemberNameException extends RuntimeException {
    private final BaseResponseCode errorCode;

    public DuplicateMemberNameException(BaseResponseCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}