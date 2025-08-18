package com.example.spring_member_management.repository;

import com.example.spring_member_management.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface JpaMemberRepositoryCustom {
    Optional<Member> findMemberDetailById(Long memberId);

    Page<Member> findMembersWithTeam(Pageable pageable);
}