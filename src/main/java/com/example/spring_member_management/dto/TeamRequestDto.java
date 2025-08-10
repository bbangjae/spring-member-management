package com.example.spring_member_management.dto;

import com.example.spring_member_management.entity.Team;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamRequestDto {

    private String teamName;

    public Team toEntity() {
        return Team.builder()
                .teamName(teamName)
                .build();
    }
}
