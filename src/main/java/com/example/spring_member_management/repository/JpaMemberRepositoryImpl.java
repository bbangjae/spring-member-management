package com.example.spring_member_management.repository;

import com.example.spring_member_management.entity.Member;
import com.example.spring_member_management.entity.QLocker;
import com.example.spring_member_management.entity.QMember;
import com.example.spring_member_management.entity.QTeam;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
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

    @Override
    public Page<Member> findMembersWithTeam(Pageable pageable) {
        QMember member = new QMember("member");
        QTeam team = new QTeam("team");

        List<Member> results = queryFactory.selectFrom(member)
                .leftJoin(member.team, team).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(member.count())
                .from(member);

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }
}