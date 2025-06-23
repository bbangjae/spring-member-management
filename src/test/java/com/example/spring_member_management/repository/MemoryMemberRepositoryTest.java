package com.example.spring_member_management.repository;

import com.example.spring_member_management.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();

    @AfterEach
    void afterEach() {
        memoryMemberRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Member member = new Member();
        member.setMemberName("SAVE TEST");

        // when
        Member saveMember = memoryMemberRepository.save(member);

        //then
        assertThat(saveMember).isEqualTo(member);
    }

    @Test
    void findById() {
        //given
        Member member = new Member();
        member.setMemberName("NAME");
        Member saveMember = memoryMemberRepository.save(member);

        // when
        Member findMember = memoryMemberRepository.findById(saveMember.getMemberId())
                .orElse(null);

        //then
        assertThat(findMember).isEqualTo(saveMember);
    }

    @Test
    void findByName() {
        //given
        Member member = new Member();
        member.setMemberName("NAME");
        Member saveMember = memoryMemberRepository.save(member);

        // when
        Member findMember = memoryMemberRepository.findByName(saveMember.getMemberName())
                .orElse(null);

        //then
        assertThat(findMember).isEqualTo(saveMember);
    }

    @Test
    void findAll() {
        //given
        Member member = new Member();
        member.setMemberName("NAME");
        memoryMemberRepository.save(member);

        Member member2 = new Member();
        member2.setMemberName("NAME2");
        memoryMemberRepository.save(member2);

        // when
        List<Member> findMembers = memoryMemberRepository.findAll();

        //then
        assertThat(findMembers).size().isEqualTo(2);
        assertThat(findMembers).contains(member, member2);
    }
}