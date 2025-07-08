package com.example.spring_member_management.service;

import com.example.spring_member_management.domain.Member;
import com.example.spring_member_management.dto.MemberRequestDto;
import com.example.spring_member_management.dto.MemberResponseDto;
import com.example.spring_member_management.exception.BaseResponseCode;
import com.example.spring_member_management.exception.DuplicateMemberNameException;
import com.example.spring_member_management.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long createMember(MemberRequestDto memberRequest) {
        Member member = new Member(memberRequest.getMemberName());

        validateUniqueMemberName(member.getMemberName());

        return memberRepository.save(member).getMemberId();
    }

    /**
     * 회원 ID로 회원 조회
     */
    public MemberResponseDto findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .map(member -> MemberResponseDto.builder()
                        .memberId(member.getMemberId())
                        .memberName(member.getMemberName())
                        .build())
                .orElse(null);
    public MemberResponseDto getMemberById(Long memberId) {
    }

    /**
     * 전체 회원 목록 조회
     */
    public List<MemberResponseDto> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(member -> MemberResponseDto.builder()
                        .memberId(member.getMemberId())
                        .memberName(member.getMemberName())
                        .build())
                .toList();
    }

    private void validateDuplicateMemberName(Member member) {
        if (memberRepository.findByName(member.getMemberName()).isPresent()) {
            throw new DuplicateMemberNameException(BaseResponseCode.DUPLICATE_MEMBER_NAME);
        }
    }
}
