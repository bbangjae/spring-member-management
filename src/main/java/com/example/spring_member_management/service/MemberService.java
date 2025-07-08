package com.example.spring_member_management.service;

import com.example.spring_member_management.domain.Member;
import com.example.spring_member_management.dto.MemberRequestDto;
import com.example.spring_member_management.dto.MemberResponseDto;
import com.example.spring_member_management.exception.BaseResponseCode;
import com.example.spring_member_management.exception.DuplicateMemberNameException;
import com.example.spring_member_management.exception.MemberNotFoundException;
import com.example.spring_member_management.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;


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
    public MemberResponseDto getMemberById(Long memberId) {
        Member member = findMemberById(memberId);
        return MemberResponseDto.builder()
                .memberId(member.getMemberId())
                .memberName(member.getMemberName())
                .build();
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

    /**
     * 회원 이름 변경
     */
    @Transactional
    public void updateMemberNameById(Long memberId, String newName) {
        Member member = findMemberById(memberId);

        if (!member.getMemberName().equals(newName)) {
            validateUniqueMemberName(newName);
        }

        member.updateName(newName);
        memberRepository.save(member);
    }

    /**
     * 회원 삭제
     */
    @Transactional
    public void deleteMemberById(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    private void validateUniqueMemberName(String memberName) {
        if (memberRepository.findByName(memberName).isPresent()) {
            throw new DuplicateMemberNameException(BaseResponseCode.DUPLICATE_MEMBER_NAME);
        }
    }

    private Member findMemberById(Long memberId) {
        if (memberRepository.findById(memberId).isEmpty()) {
            throw new MemberNotFoundException(BaseResponseCode.DATA_NOT_FOUND);
        }
        return memberRepository.findById(memberId).get();
    }
}
