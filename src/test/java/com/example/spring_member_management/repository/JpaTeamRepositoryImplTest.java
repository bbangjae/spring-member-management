package com.example.spring_member_management.repository;

import com.example.spring_member_management.dto.TeamWithMemberCountDto;
import com.example.spring_member_management.entity.Member;
import com.example.spring_member_management.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Test
    void 팀_목록_조회_페이징_성공() {
        //given
        for (int i = 1; i < 12; i++) {
            Team team = new Team("Team" + i);
            jpaTeamRepository.save(team);
        }

        Team team12 =  new Team("Team12");
        Member member = new Member("Member1", null, team12);
        Member member2 = new Member("Member2", null, team12);

        em.persist(team12);
        em.persist(member);
        em.persist(member2);

        em.flush();
        em.clear();
        //when
        PageRequest pageRequest = PageRequest.of(1, 10);
        Page<Team> result = jpaTeamRepository.findTeamsWithMemberCount(pageRequest);

        //then
        assertThat(result.getContent())
                .filteredOn(team -> team.getName().equals("Team12"))
                .extracting(Team::getName, team -> team.getMembers().size())
                .containsExactly(tuple("Team12", 2));

        assertThat(result.getTotalElements()).isEqualTo(12);
        assertThat(result.getTotalPages()).isEqualTo(2);
    }
}