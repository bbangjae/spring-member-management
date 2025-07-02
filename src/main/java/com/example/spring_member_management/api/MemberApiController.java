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
    public ResponseEntity<BaseResponse<List<MemberResponseDto>>> listMembers() {
        List<MemberResponseDto> members = memberService.findAllMembers();
        return ResponseEntity.ok(BaseResponse.success(members));
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> createMember(@RequestBody MemberRequestDto request) {
        memberService.join(request);
        return ResponseEntity.ok(BaseResponse.success(null));
    }
}