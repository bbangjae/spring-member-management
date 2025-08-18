package com.example.spring_member_management.service.jpa;

import com.example.spring_member_management.dto.TeamDetailResponseDto;
import com.example.spring_member_management.dto.TeamRequestDto;
import com.example.spring_member_management.dto.TeamResponseDto;
import com.example.spring_member_management.dto.TeamWithMemberCountDto;
import com.example.spring_member_management.entity.Team;
import com.example.spring_member_management.exception.BaseResponseCode;
import com.example.spring_member_management.exception.DuplicateMemberNameException;
import com.example.spring_member_management.exception.TeamNotFoundException;
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
     * 팀 생성
     */
    @Transactional
    public Long createTeam(TeamRequestDto teamRequestDto) {
        validateUniqueTeamName(teamRequestDto.getTeamName());

        Team newTeam = teamRequestDto.toEntity();
        return teamRepository.save(newTeam).getId();
    }

    /**
     * 전체 팀 목록 조회
     */
    public List<TeamResponseDto> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(TeamResponseDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 전체 팀 목록(회원 정보 포함) 조회
     */
    public TeamDetailResponseDto getTeamDetailById(Long teamId) {
        return teamRepository.findTeamDetailById(teamId)
                .map(TeamDetailResponseDto::of)
                .orElseThrow(() -> new TeamNotFoundException(BaseResponseCode.DATA_NOT_FOUND));
    }

    /**
     * 전체 팀 목록 with 멤버 수
     */
    public List<TeamWithMemberCountDto> getAllTeamsWithMemberCount() {
        return teamRepository.findAllTeamsWithMemberCount();
    }

    /**
     * 팀 명 수정
     */
    @Transactional
    public void updateTeamName(Long teamId, TeamRequestDto teamRequestDto) {
        Team team = findTeamById(teamId);
        validateUniqueTeamName(teamRequestDto.getTeamName());

        team.changeName(teamRequestDto.getTeamName());
    }

    /**
     * 팀 삭제
     */
    @Transactional
    public void deleteTeamById(Long teamId) {
        findTeamById(teamId);
        teamRepository.deleteById(teamId);
    }

    private void validateUniqueTeamName(String teamName) {
        if (teamRepository.existsByName(teamName)) {
            throw new DuplicateMemberNameException(BaseResponseCode.DUPLICATE_TEAM_NAME);
        }
    }

    private Team findTeamById(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(BaseResponseCode.DATA_NOT_FOUND));
    }
}