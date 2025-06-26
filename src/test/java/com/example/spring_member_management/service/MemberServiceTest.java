package com.example.spring_member_management.service;

import com.example.spring_member_management.domain.Member;
import com.example.spring_member_management.dto.MemberDto;
import com.example.spring_member_management.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memoryMemberRepository;

    @BeforeEach
    void BeforeEach() {
        this.memoryMemberRepository = new MemoryMemberRepository();
        this.memberService = new MemberService(memoryMemberRepository);
    }

    @AfterEach
    void AfterEach() {
        memoryMemberRepository.clearStore();
    }

    @Test
    void 회원가입_성공() {
        //given
        MemberDto memberDto = createMemberDto("memberName");

        //when
        Long savedMemberId= memberService.join(memberDto);

        //then
        Optional<Member> foundMember = memoryMemberRepository.findById(savedMemberId);
        assertThat(foundMember).isPresent()
                .get().extracting(Member::getMemberName).isEqualTo("memberName");
    }

    @Test
    void 회원가입_중복회원_예외발생() {
        //given
        MemberDto member1 = createMemberDto("NAME1");;
        MemberDto member2 = createMemberDto("NAME1");
        memberService.join(member1);

        //when & then
        assertThrows(IllegalStateException.class,
                () -> memberService.join(member2),
                "이미 존재하는 회원명입니다.");
    }

    @Test
    void 전체회원_조회() {
        //given
        MemberDto member1 = createMemberDto("NAME1");;
        memberService.join(member1);

        MemberDto member2 = createMemberDto("NAME2");
        memberService.join(member2);

        //when
        List<Member> members = memberService.findAllMembers();

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
        MemberDto memberDto = createMemberDto("user1");
        Long savedId = memberService.join(memberDto);

        //when
        Optional<Member> foundMember = memberService.findMemberById(savedId);

        //then
        assertThat(foundMember)
                .isPresent()
                .get()
                .extracting(Member::getMemberName).isEqualTo("user1");
    }

    private MemberDto createMemberDto(String memberName) {
        MemberDto memberDto = new MemberDto();
        memberDto.setName(memberName);
        return memberDto;
    }
}