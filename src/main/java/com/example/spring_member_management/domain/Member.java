package com.example.spring_member_management.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Member {
    @Setter
    private Long memberId;
    private String memberName;

    public Member(String memberName) {
        this.memberName = memberName;
    }
}
