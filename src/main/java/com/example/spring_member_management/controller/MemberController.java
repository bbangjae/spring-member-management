package com.example.spring_member_management.controller;

import com.example.spring_member_management.domain.Member;
import com.example.spring_member_management.dto.MemberDto;
import com.example.spring_member_management.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public String listMembers(Model model) {
        List<Member> members = memberService.findAllMembers();
        model.addAttribute("members", members);
        return "members/member-list";
    }

    @GetMapping("/members/new")
    public String showCreateMemberForm() {
        return "members/create-member-form";
    }

    @PostMapping("/members/new")
    public String createMember(MemberDto memberRequest) {
        Member member = new Member();
        member.setMemberName(memberRequest.getName());
        memberService.join(member);
        return "redirect:/";
    }
}
