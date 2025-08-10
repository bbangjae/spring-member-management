package com.example.spring_member_management.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BaseResponseCode {
    SUCCESS(HttpStatus.OK, "요청 성공"),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력값"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증 실패"),
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "데이터 없음"),
    DUPLICATE_MEMBER_NAME(HttpStatus.CONFLICT, "이미 존재하는 회원명입니다."),
    DUPLICATE_TEAM_NAME(HttpStatus.CONFLICT, "이미 존재하는 팀명입니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류");

    private final HttpStatus status;
    private final String message;

    public int getCode() {
        return status.value();
    }
}