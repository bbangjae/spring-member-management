package com.example.spring_member_management.repository;

import com.example.spring_member_management.dto.TeamWithMemberCountDto;
import com.example.spring_member_management.entity.Team;

import java.util.List;
import java.util.Optional;

public interface JpaTeamRepositoryCustom {
    List<TeamWithMemberCountDto> findAllTeamsWithMemberCount();

    Optional<Team> findTeamDetailById(Long teamId);
}