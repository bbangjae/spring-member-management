package com.example.spring_member_management.repository;

import com.example.spring_member_management.dto.TeamWithMemberCountDto;
import com.example.spring_member_management.entity.Member;
import com.example.spring_member_management.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@Transactional
class JpaTeamRepositoryImplTest {

    @Autowired
    JpaTeamRepository jpaTeamRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    void 팀_목록_회원_수_조회_성공() {
        //given
        Team team1 = Team.builder()
                .teamName("개발팀")
                .build();

        Team team2 = Team.builder()
                .teamName("홍보팀")
                .build();

        Member member1 = Member.builder()
                .memberName("jae")
                .team(team1)
                .build();

        em.persist(team1);
        em.persist(team2);
        em.persist(member1);
        em.flush();
        em.clear();

        //when
        List<TeamWithMemberCountDto> foundTeams = jpaTeamRepository.findAllTeamsWithMemberCount();

        //then
        assertThat(foundTeams)
                .extracting("teamName", "memberCount")
                .containsExactlyInAnyOrder(
                        tuple("홍보팀", 0L),
                        tuple("개발팀", 1L)
                );
    }
}