package com.example.spring_member_management.dto;

import com.example.spring_member_management.entity.Team;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamWithMemberCountDto {

    private Long teamId;
    private String teamName;
    private Long memberCount;

    public static TeamWithMemberCountDto of(Team team) {

        return TeamWithMemberCountDto.builder()
                .teamId(team.getId())
                .teamName(team.getName())
                .memberCount(team.getMembers() == null ? 0L : (long) team.getMembers().size())
                .build();
    }
}