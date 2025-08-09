package com.example.spring_member_management.repository;

import com.example.spring_member_management.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JpaMemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByName(String name);

    @EntityGraph(attributePaths = "team")
    @Query("SELECT m FROM Member m")
    List<Member> findAllWithTeam();
}