package com.example.spring_member_management.api.jpa;

import com.example.spring_member_management.dto.TeamRequestDto;
import com.example.spring_member_management.service.jpa.JpaTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}