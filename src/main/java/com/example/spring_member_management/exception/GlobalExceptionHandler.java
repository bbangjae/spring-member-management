package com.example.spring_member_management.exception;

import com.example.spring_member_management.common.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateMemberNameException.class)
    public ResponseEntity<BaseResponse<Void>> handleDuplicateMemberName(DuplicateMemberNameException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(BaseResponse.failure(e.getErrorCode()));
    }
}