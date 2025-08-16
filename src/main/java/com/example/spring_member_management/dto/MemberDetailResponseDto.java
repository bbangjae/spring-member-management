package com.example.spring_member_management.dto;

import com.example.spring_member_management.entity.Address;
import com.example.spring_member_management.entity.Locker;
import com.example.spring_member_management.entity.Member;
import com.example.spring_member_management.entity.Team;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetailResponseDto {

    private Long memberId;
    private String memberName;
    private Long teamId;
    private String teamName;
    private Address address;
    private Long lockerId;
    private String lockerNumber;

    public static MemberDetailResponseDto of(Member member) {
        final Team team = member.getTeam();
        final Locker locker = member.getLocker();

        return MemberDetailResponseDto.builder()
                .memberId(member.getId())
                .memberName(member.getName())
                .teamId(team == null ? null : team.getId())
                .teamName(team == null ? null : team.getName())
                .address(member.getAddress())
                .lockerId(locker == null ? null : locker.getId())
                .lockerNumber(locker == null ? null : locker.getNumber())
                .build();
    }
}