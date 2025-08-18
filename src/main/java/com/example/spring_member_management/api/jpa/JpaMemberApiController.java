package com.example.spring_member_management.api.jpa;

import com.example.spring_member_management.common.BaseResponse;
import com.example.spring_member_management.dto.MemberUpdateRequestDto;
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
        memberService.createMember(memberRequestDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{memberId}/edit")
    public ResponseEntity<Void> updateMember(
            @PathVariable Long memberId,
            @RequestBody MemberUpdateRequestDto memberUpdateRequestDto) {
        memberService.updateMember(memberId, memberUpdateRequestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{memberId}/delete")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMemberById(memberId);
        return ResponseEntity.noContent().build();
    }
}