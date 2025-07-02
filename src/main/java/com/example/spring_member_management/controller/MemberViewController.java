package com.example.spring_member_management.controller;

import com.example.spring_member_management.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberViewController {

    private final MemberService memberService;

    @GetMapping
    public String showMemberListPage() {
        return "members/member-list";
    }

    @GetMapping("/new")
    public String showCreateMemberForm() {
        return "members/create-member-form";
    }
}