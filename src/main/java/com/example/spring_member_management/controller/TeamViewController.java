package com.example.spring_member_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teams")
public class TeamViewController {

    @GetMapping("/new")
    public String showCreateTeamForm() {
        return "teams/create-team-form";
    }

    @GetMapping
    public String showTeamListPage() {
        return "teams/team-list";
    }
}