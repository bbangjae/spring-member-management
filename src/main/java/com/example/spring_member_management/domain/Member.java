package com.example.spring_member_management.domain;

public class Member {
    private Long memberId;
    private String memberName;

    public Member(String memberName) {
        this.memberName = memberName;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
    public Long getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }
}
