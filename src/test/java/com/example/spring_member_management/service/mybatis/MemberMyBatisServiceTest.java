package com.example.spring_member_management.service.mybatis;

import com.example.spring_member_management.domain.Member;
import com.example.spring_member_management.mapper.MemberMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberMyBatisServiceTest {

    @Autowired MemberMyBatisService memberMyBatisService;
    @Autowired MemberMapper memberMapper;

    @Test
    void 회원가입_성공() {
        //given
        Member member = createMember("memberName");

        //when
        Long savedMemberId = memberMyBatisService.join(member);

        //then
        Optional<Member> foundMember = memberMapper.findById(savedMemberId);
        assertThat(foundMember).isPresent()
                .get().extracting(Member::getMemberName).isEqualTo("memberName");
    }

    @Test
    void 회원가입_중복회원_예외발생() {
        //given
        Member member1 = createMember("NAME1");;
        Member member2 = createMember("NAME1");
        memberMyBatisService.join(member1);

        //when & then
        assertThrows(IllegalStateException.class,
                () -> memberMyBatisService.join(member2),
                "이미 존재하는 회원명입니다.");
    }

    @Test
    void 전체회원_조회() {
        //given
        memberMyBatisService.join(createMember("NAME1"));
        memberMyBatisService.join(createMember("NAME2"));

        //when
        List<Member> members = memberMyBatisService.findAllMembers();

        //then
        assertThat(members)
                .hasSize(2)
                .extracting(Member::getMemberName)
                .containsExactlyInAnyOrder("NAME1", "NAME2");
    }

    // 추가 테스트 케이스
    @Test
    void 회원_단건_조회() {
        //given
        Member member = createMember("NAME1");
        Long savedId = memberMyBatisService.join(member);

        //when
        Optional<Member> foundMember = memberMyBatisService.findMemberById(savedId);

        //then
        assertThat(foundMember)
                .isPresent()
                .hasValueSatisfying(m -> {
                    assertThat(m.getMemberId()).isEqualTo(savedId);
                    assertThat(m.getMemberName()).isEqualTo("NAME1");
                });
    }

    private Member createMember(String memberName) {
        Member member = new Member();
        member.setMemberName(memberName);
        return member;
    }
}