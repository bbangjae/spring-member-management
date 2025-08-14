package com.example.spring_member_management.repository;

import com.example.spring_member_management.dto.TeamWithMemberCountDto;
import static com.example.spring_member_management.entity.QTeam.team;
import static com.example.spring_member_management.entity.QMember.member;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class JpaTeamRepositoryImpl implements JpaTeamRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<TeamWithMemberCountDto> findAllTeamsWithMemberCount() {
        return jpaQueryFactory
                .select(Projections.constructor(TeamWithMemberCountDto.class,
                        team.id,
                        team.name,
                        member.countDistinct()))
                .from(team)
                .leftJoin(team.members, member)
                .groupBy(team.id, team.name)
                .fetch();
    }
}