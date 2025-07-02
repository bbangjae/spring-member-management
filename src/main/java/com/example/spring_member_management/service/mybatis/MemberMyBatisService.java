package com.example.spring_member_management.service.mybatis;

import com.example.spring_member_management.domain.Member;
import com.example.spring_member_management.dto.MemberRequestDto;
import com.example.spring_member_management.dto.MemberResponseDto;
import com.example.spring_member_management.exception.BaseResponseCode;
import com.example.spring_member_management.exception.DuplicateMemberNameException;
import com.example.spring_member_management.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberMyBatisService {
    private final MemberMapper memberMapper;

    @Autowired
    public MemberMyBatisService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    /**
     * 회원가입
     */
    @Transactional
    public Long join(MemberRequestDto memberRequest) {
        Member member = new Member(memberRequest.getMemberName());

        validateDuplicateMemberName(member);

        memberMapper.save(member);

        return member.getMemberId();
    }

    /**
     * 회원 ID로 회원 조회
     */
    public MemberResponseDto findMemberById(Long memberId) {
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
    public List<MemberResponseDto> findAllMembers() {
        return memberMapper.findAll()
                .stream()
                .map(member -> MemberResponseDto.builder()
                        .memberId(member.getMemberId())
                        .memberName(member.getMemberName())
                        .build())
                .toList();
    }

    private void validateDuplicateMemberName(Member member) {
        if (memberMapper.findByName(member.getMemberName()).isPresent()) {
            throw new DuplicateMemberNameException(BaseResponseCode.DUPLICATE_MEMBER_NAME);
        }
    }
}