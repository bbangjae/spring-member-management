package com.example.spring_member_management.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BaseEntityTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    void 생성시_생성일_생성자정보_성공() {
        //given
        Member member = new Member("jae", new Address("서울", "신림", "1234"), null);
        em.persist(member);

        em.flush();
        em.clear();

        //when
        Member foundMember = em.find(Member.class, member.getId());

        //then
        assertThat(foundMember.getCreatedBy()).isEqualTo("jae");
        assertThat(foundMember.getLastModifiedBy()).isEqualTo("jae");
        assertThat(foundMember.getCreatedDate()).isNotNull();
        assertThat(foundMember.getLastModifiedDate()).isNotNull();
    }

    @Test
    void 변경시_수정날짜_변경_성공() {
        //given
        Member member = new Member("jae", new Address("서울", "신림", "1234"), null);
        em.persist(member);
        em.flush();
        em.clear();

        //when
        Member foundMember = em.find(Member.class, member.getId());
        foundMember.changeName("byun");

        em.flush();
        em.clear();

        Member UpdatedMember = em.find(Member.class, member.getId());

        //then
        assertThat(UpdatedMember.getName()).isEqualTo("byun");
        assertThat(UpdatedMember.getLastModifiedDate()).isAfter(UpdatedMember.getCreatedDate());
    }
}
