package com.example.spring_member_management.repository;

import com.example.spring_member_management.entity.Member;
import java.util.Optional;

public interface JpaMemberRepositoryCustom {
    Optional<Member> findMemberDetailById(Long memberId);
}