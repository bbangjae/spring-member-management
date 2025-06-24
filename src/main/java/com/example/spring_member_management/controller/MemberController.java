package com.example.spring_member_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @GetMapping("/members")
    public String showMemberList() {
        return "members/member-list";
    }

    @GetMapping("/members/new")
    public String showCreateMemberForm() {
        return "members/create-member-form";
    }
}
