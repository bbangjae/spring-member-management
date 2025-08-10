package com.example.spring_member_management.api.jpa;

import com.example.spring_member_management.common.BaseResponse;
import com.example.spring_member_management.dto.MemberRequestDto;
import com.example.spring_member_management.dto.MemberWithAddressRequestDto;
import com.example.spring_member_management.dto.MemberWithTeamResponseDto;
import com.example.spring_member_management.service.jpa.JpaMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class JpaMemberApiController {

    private final JpaMemberService memberService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<MemberWithTeamResponseDto>>> getAllMembers() {
        List<MemberWithTeamResponseDto> members = memberService.getAllMembersWithTeam();
        return ResponseEntity.ok(BaseResponse.success(members));
    }

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody MemberWithAddressRequestDto memberRequestDto) {
        System.out.println(memberRequestDto);
        memberService.createMember(memberRequestDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<Void> updateMemberName(
            @PathVariable Long memberId,
            @RequestBody MemberRequestDto request) {
        memberService.updateMemberNameById(memberId, request.getMemberName());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMemberById(memberId);
        return ResponseEntity.noContent().build();
    }
}