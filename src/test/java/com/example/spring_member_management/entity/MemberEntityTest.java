package com.example.spring_member_management.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberEntityTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    void 멤버_주소_생성_성공() {
        // given
        Address address = new Address("서울", "신림", "1234");
        Member member = new Member("jae", address, null);

        em.persist(member);
        em.flush();
        em.clear();
        // when
        Member foundMember = em.find(Member.class, member.getId());

        // then
        assertThat(foundMember.getName()).isEqualTo("jae");
        assertThat(foundMember.getAddress()).isEqualTo(address);
    }

    @Test
    void 멤버_락커_배정_성공() {
        //given
        Address address = new Address("서울", "신림", "1234");
        Member member = new Member("jae", address, null);
        Locker locker = new Locker("L1");
        member.assignLocker(locker);

        em.persist(locker);
        em.persist(member);
        em.flush();
        em.clear();

        //when
        Member foundMember = em.find(Member.class, member.getId());

        //then
        assertThat(foundMember.getLocker().getNumber()).isEqualTo("L1");
    }
}
