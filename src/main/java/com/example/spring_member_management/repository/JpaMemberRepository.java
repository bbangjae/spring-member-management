package com.example.spring_member_management.repository;

import com.example.spring_member_management.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends JpaRepository<Member, Long> {
}