package com.example.spring_member_management.repository;

import com.example.spring_member_management.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTeamRepository extends JpaRepository<Team, Long>, JpaTeamRepositoryCustom {

    boolean existsByName(String memberName);
}