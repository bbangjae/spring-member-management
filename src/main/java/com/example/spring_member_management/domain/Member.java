package com.example.spring_member_management.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Member {
    private Long memberId;
    private String memberName;

    public Member(String memberName) {
        this(null, memberName);
    }

    public Member(Long memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
    }

    public void updateName(String newName) {
        this.memberName = newName;
    }
}
