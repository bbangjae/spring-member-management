package com.example.spring_member_management.service;

import com.example.spring_member_management.domain.Member;
import com.example.spring_member_management.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {
        validateDuplicateMemberName(member);
        return memberRepository.save(member).getMemberId();
    }

    /**
     * 회원 ID로 회원 조회
     */
    public Optional<Member> findMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    /**
     * 전체 회원 목록 조회
     */
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    private void validateDuplicateMemberName(Member member) {
        memberRepository.findByName(member.getMemberName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원명입니다.");
                });
    }
}
