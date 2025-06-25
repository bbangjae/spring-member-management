package com.example.spring_member_management.service.mybatis;

import com.example.spring_member_management.domain.Member;
import com.example.spring_member_management.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public Long join(Member member) {
        validateDuplicateMemberName(member);
        memberMapper.save(member);
        return member.getMemberId();
    }

    /**
     * 회원 ID로 회원 조회
     */
    public Optional<Member> findMemberById(Long memberId) {
        return memberMapper.findById(memberId);
    }

    /**
     * 전체 회원 목록 조회
     */
    public List<Member> findAllMembers() {
        return memberMapper.findAll();
    }

    private void validateDuplicateMemberName(Member member) {
        if (memberMapper.findByName(member.getMemberName()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원명입니다.");
        }
    }
}