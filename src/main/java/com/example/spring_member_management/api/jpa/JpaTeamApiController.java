package com.example.spring_member_management.api.jpa;

import com.example.spring_member_management.common.BaseResponse;
import com.example.spring_member_management.dto.TeamRequestDto;
import com.example.spring_member_management.dto.TeamResponseDto;
import com.example.spring_member_management.dto.TeamWithMemberCountDto;
import com.example.spring_member_management.service.jpa.JpaTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class JpaTeamApiController {

    private final JpaTeamService teamService;

    @PostMapping
    public ResponseEntity<Void> createTeam(@RequestBody TeamRequestDto teamRequestDto) {
        teamService.createTeam(teamRequestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<TeamResponseDto>>> getAllTeams() {
        List<TeamResponseDto> teams = teamService.getAllTeams();
        return ResponseEntity.ok(BaseResponse.success(teams));
    }

    @GetMapping("/member-count")
    public ResponseEntity<BaseResponse<List<TeamWithMemberCountDto>>> getAllTeamsWithMemberCount() {
        List<TeamWithMemberCountDto> teams = teamService.getAllTeamsWithMemberCount();
        return ResponseEntity.ok(BaseResponse.success(teams));
    }

    @PatchMapping("/{teamId}/edit")
    public ResponseEntity<Void> updateTeamName(@PathVariable Long teamId, @RequestBody TeamRequestDto teamRequestDto) {
        teamService.updateTeamName(teamId, teamRequestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{teamId}/delete")
    public ResponseEntity<Void> updateTeamName(@PathVariable Long teamId) {
        teamService.deleteTeamById(teamId);
        return ResponseEntity.noContent().build();
    }
}