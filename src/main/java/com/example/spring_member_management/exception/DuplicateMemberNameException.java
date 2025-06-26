package com.example.spring_member_management.exception;

public class DuplicateMemberNameException extends RuntimeException {
    public DuplicateMemberNameException(String message) {
        super(message);
    }
}