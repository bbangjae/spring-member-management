package com.example.spring_member_management.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamWithMemberCountDto {

    private Long teamId;
    private String teamName;
    private Long memberCount;
}