package com.example.spring_member_management.repository;

import com.example.spring_member_management.dto.TeamWithMemberCountDto;

import java.util.List;

public interface JpaTeamRepositoryCustom {
    List<TeamWithMemberCountDto> findAllTeamsWithMemberCount();
}