package com.example.spring_member_management.service.jpa;

import com.example.spring_member_management.dto.TeamResponseDto;
import com.example.spring_member_management.repository.JpaTeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaTeamService {

    private final JpaTeamRepository teamRepository;

    /**
     * 전체 팀 목록 조회
     */
    public List<TeamResponseDto> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(TeamResponseDto::of)
                .collect(Collectors.toList());
    }
}