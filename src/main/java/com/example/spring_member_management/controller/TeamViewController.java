package com.example.spring_member_management.controller;

import com.example.spring_member_management.dto.TeamDetailResponseDto;
import com.example.spring_member_management.service.jpa.JpaTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamViewController {

    private final JpaTeamService teamService;
    @GetMapping("/new")
    public String showCreateTeamForm() {
        return "teams/create-team-form";
    }

    @GetMapping
    public String showTeamListPage() {
        return "teams/team-list";
    }

    @GetMapping("/{teamId}")
    public String showTeamDetailPage(@PathVariable("teamId") Long teamId, Model model) {
        TeamDetailResponseDto team = teamService.getTeamDetailById(teamId);
        model.addAttribute("team", team);
        return "teams/team-detail";
    }
}