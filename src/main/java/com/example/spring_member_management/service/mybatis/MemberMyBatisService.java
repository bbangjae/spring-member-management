package com.example.spring_member_management.service.mybatis;

import com.example.spring_member_management.domain.Member;
import com.example.spring_member_management.dto.MemberRequestDto;
import com.example.spring_member_management.dto.MemberResponseDto;
import com.example.spring_member_management.exception.BaseResponseCode;
import com.example.spring_member_management.exception.DuplicateMemberNameException;
import com.example.spring_member_management.exception.MemberNotFoundException;
import com.example.spring_member_management.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberMyBatisService {
    private final MemberMapper memberMapper;

    /**
     * 회원가입
     */
    @Transactional
    public Long createMember(MemberRequestDto memberRequest) {
        Member member = new Member(memberRequest.getMemberName());

        validateUniqueMemberName(member.getMemberName());

        memberMapper.save(member);

        return member.getMemberId();
    }

    /**
     * 회원 ID로 회원 조회
     */
    public MemberResponseDto getMemberById(Long memberId) {
        return memberMapper.findById(memberId)
                .map(member -> MemberResponseDto.builder()
                        .memberId(member.getMemberId())
                        .memberName(member.getMemberName())
                        .build())
                .orElse(null);
    }

    /**
     * 전체 회원 목록 조회
     */
    public List<MemberResponseDto> getAllMembers() {
        return memberMapper.findAll()
                .stream()
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

        memberMapper.updateNameById(newName,  memberId);
    }

    private void validateUniqueMemberName(String memberName) {
        if (memberMapper.findByName(memberName).isPresent()) {
            throw new DuplicateMemberNameException(BaseResponseCode.DUPLICATE_MEMBER_NAME);
        }
    }

    private Member findMemberById(Long memberId) {
        return memberMapper.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(BaseResponseCode.DATA_NOT_FOUND));
    }
}