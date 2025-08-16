package com.example.spring_member_management.controller;

import com.example.spring_member_management.dto.MemberDetailResponseDto;
import com.example.spring_member_management.service.jpa.JpaMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberViewController {

    private final JpaMemberService jpaMemberService;

    @GetMapping
    public String showMemberListPage() {
        return "members/member-list";
    }

    @GetMapping("/new")
    public String showCreateMemberForm() {
        return "members/create-member-form";
    }

    @GetMapping("/{memberId}")
    public String showMemberDetailPage(@PathVariable Long memberId, Model model) {
        MemberDetailResponseDto member= jpaMemberService.getMemberDetailById(memberId);
        model.addAttribute("member", member);
        return "members/member-detail";
    }
}