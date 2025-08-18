package com.example.spring_member_management.repository;

import com.example.spring_member_management.entity.Address;
import com.example.spring_member_management.entity.Member;
import com.example.spring_member_management.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class JpaMemberRepositoryTest {
    @Autowired
    private JpaMemberRepository jpaMemberRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    void 멤버_저장_조회_성공() {
        //given
        Member member = new Member("jae", new Address("서울", "신림", "1234"), null);

        //when
        jpaMemberRepository.save(member);
        Optional<Member> foundMember = jpaMemberRepository.findById(member.getId());

        //then
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getName()).isEqualTo("jae");
    }

    @Test
    void 멤버_이름_변경_성공() {
        //given
        Member member = new Member("jae", new Address("서울", "신림", "1234"), null);

        //when
        Member savedMember = jpaMemberRepository.save(member);
        savedMember.changeName("byun");

        em.flush();
        em.clear();

        //then
        Member foundMember = jpaMemberRepository.findById(savedMember.getId()).orElseThrow();
        assertThat(foundMember.getName()).isEqualTo("byun");
    }

    @Test
    void 모든_멤버_조회_성공() {
        //given
        jpaMemberRepository.save(new Member("jae", new Address("서울", "신림", "1234"), null));
        jpaMemberRepository.save(new Member("jae2", new Address("서울", "신림", "1234"), null));
        jpaMemberRepository.save(new Member("jae3", new Address("서울", "신림", "1234"), null));
        jpaMemberRepository.save(new Member("jae4", new Address("서울", "신림", "1234"), null));

        //when
        List<Member> foundMembers = jpaMemberRepository.findAll();

        //then
        assertThat(foundMembers).hasSize(4);
    }

    @Test
    void 멤버_삭제_성공() {
        //given
        Member savedMember = jpaMemberRepository.save(new Member("jae", new Address("서울", "신림", "1234"), null));

        //when
        jpaMemberRepository.deleteById(savedMember.getId());

        em.flush();
        em.clear();

        //then
        assertThat(jpaMemberRepository.findById(savedMember.getId())).isEmpty();
    }

    @Test
    void 멤버_리스트_페이징_성공() {
        //given
        for (int i = 1; i < 13; i++) {
            Team team = new Team("team" + i);
            Member member = new Member("member" + i, null, team);
            em.persist(team);
            em.persist(member);
        }
        em.flush();
        em.clear();

        Pageable pageable = PageRequest.of(1, 10);

        // when
        Page<Member> result = jpaMemberRepository.findMembersWithTeam(pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(12);
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getContent()).hasSize(2);

        assertThat(result.getContent())
                .element(0)
                .extracting(Member::getName)
                .isEqualTo("member11");

        assertThat(result.getContent())
                .element(0)
                .extracting(Member::getTeam)
                .extracting(Team::getName)
                .isEqualTo("team11");
    }
}