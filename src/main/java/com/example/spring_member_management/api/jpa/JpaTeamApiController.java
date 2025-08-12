package com.example.spring_member_management.api.jpa;

import com.example.spring_member_management.common.BaseResponse;
import com.example.spring_member_management.dto.TeamRequestDto;
import com.example.spring_member_management.dto.TeamResponseDto;
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
        List<TeamResponseDto> team = teamService.getAllTeams();
        return ResponseEntity.ok(BaseResponse.success(team));
    }
}