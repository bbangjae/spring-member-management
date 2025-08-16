package com.example.spring_member_management.dto;

import com.example.spring_member_management.entity.Locker;
import com.example.spring_member_management.entity.Member;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSimpleDto {

    private Long memberId;
    private String memberName;
    private String lockerNumber;

    public static MemberSimpleDto of(Member member) {
        Locker locker = member.getLocker();

        return MemberSimpleDto.builder()
                .memberId(member.getId())
                .memberName(member.getName())
                .lockerNumber(locker == null ? null : locker.getNumber())
                .build();
    }
}