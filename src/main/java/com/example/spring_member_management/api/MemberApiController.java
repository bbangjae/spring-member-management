package com.example.spring_member_management.api;

import com.example.spring_member_management.common.BaseResponse;
import com.example.spring_member_management.dto.MemberRequestDto;
import com.example.spring_member_management.dto.MemberResponseDto;
import com.example.spring_member_management.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<MemberResponseDto>>> getAllMembers() {
        List<MemberResponseDto> members = memberService.getAllMembers();
        return ResponseEntity.ok(BaseResponse.success(members));
    }

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody MemberRequestDto request) {
        memberService.createMember(request);
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