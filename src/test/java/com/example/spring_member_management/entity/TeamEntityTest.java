package com.example.spring_member_management.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class TeamEntityTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    void 팀_멤버_추가_양방향_성공() {
        //given
        Team team = new Team("백엔드팀");
        Member member = new Member("jae", new Address("서울", "신림", "1234"), null);

        member.changeTeam(team);
        em.persist(team);
        em.flush();
        em.clear();

        //when
        Team foundTeam = em.find(Team.class, team.getId());
        Member foundMember = em.find(Member.class, member.getId());

        //then
        assertThat(foundTeam.getMembers()).hasSize(1);
        assertThat(foundTeam.getMembers().get(0).getName()).isEqualTo("jae");
        assertThat(foundMember.getTeam().getName()).isEqualTo("백엔드팀");
    }
}