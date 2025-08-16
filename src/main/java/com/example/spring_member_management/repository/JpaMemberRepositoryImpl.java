package com.example.spring_member_management.repository;

import com.example.spring_member_management.entity.Member;
import com.example.spring_member_management.entity.QLocker;
import com.example.spring_member_management.entity.QMember;
import com.example.spring_member_management.entity.QTeam;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@RequiredArgsConstructor
public class JpaMemberRepositoryImpl implements JpaMemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Member> findMemberDetailById(Long memberId) {
        QMember member = new QMember("member");
        QTeam team = new QTeam("team");
        QLocker locker = new QLocker("locker");

        return Optional.ofNullable(
                queryFactory
                        .selectFrom(member)
                        .leftJoin(member.team, team).fetchJoin()
                        .leftJoin(member.locker, locker).fetchJoin()
                        .where(member.id.eq(memberId))
                        .fetchOne()
        );
    }
}