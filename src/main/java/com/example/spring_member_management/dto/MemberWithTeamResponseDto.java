package com.example.spring_member_management.dto;

import com.example.spring_member_management.entity.Member;
import com.example.spring_member_management.entity.Team;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberWithTeamResponseDto {

    private Long memberId;
    private String memberName;
    private Long teamId;
    private String teamName;

    public static MemberWithTeamResponseDto of(Member member) {
        final Team team = member.getTeam();

        return MemberWithTeamResponseDto.builder()
                .memberId(member.getId())
                .memberName(member.getName())
                .teamId(team == null ? null : team.getId())
                .teamName(team == null ? null : team.getName())
                .build();
    }
}