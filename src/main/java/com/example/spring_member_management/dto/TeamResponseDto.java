package com.example.spring_member_management.dto;

import com.example.spring_member_management.entity.Team;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TeamResponseDto {

    private Long teamId;
    private String teamName;

    public static TeamResponseDto of(Team team) {
        return TeamResponseDto.builder()
                .teamId(team.getId())
                .teamName(team.getName())
                .build();
    }
}
