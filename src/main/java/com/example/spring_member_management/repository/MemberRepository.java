package com.example.spring_member_management.repository;

import com.example.spring_member_management.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
    void deleteById(Long id);
}
