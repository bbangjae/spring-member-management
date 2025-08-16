package com.example.spring_member_management.repository;

import com.example.spring_member_management.dto.TeamWithMemberCountDto;
import static com.example.spring_member_management.entity.QTeam.team;
import static com.example.spring_member_management.entity.QMember.member;

import com.example.spring_member_management.entity.QLocker;
import com.example.spring_member_management.entity.QMember;
import com.example.spring_member_management.entity.QTeam;
import com.example.spring_member_management.entity.Team;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Team> findTeamDetailById(Long teamId) {
        QTeam team = new QTeam("team");
        QMember member = new QMember("member");
        QLocker locker = new QLocker("locker");

        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(team).distinct()
                        .leftJoin(team.members, member).fetchJoin()
                        .leftJoin(member.locker, locker).fetchJoin()
                        .where(team.id.eq(teamId))
                        .fetchOne()
        );
    }
}