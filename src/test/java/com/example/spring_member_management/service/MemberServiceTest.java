package com.example.spring_member_management.service;

import com.example.spring_member_management.domain.Member;
import com.example.spring_member_management.dto.MemberRequestDto;
import com.example.spring_member_management.dto.MemberResponseDto;
import com.example.spring_member_management.exception.DuplicateMemberNameException;
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
    void beforeEach() {
        this.memoryMemberRepository = new MemoryMemberRepository();
        this.memberService = new MemberService(memoryMemberRepository);
    }

    @AfterEach
    void afterEach() {
        memoryMemberRepository.clearStore();
    }

    @Test
    void 회원가입_성공() {
        //given
        MemberRequestDto memberRequestDto = createMemberDto("memberName");

        //when
        Long savedMemberId = memberService.createMember(memberRequestDto);
        Optional<Member> foundMember = memoryMemberRepository.findById(savedMemberId);

        //then
        assertThat(foundMember)
                .isPresent()
                .get()
                .extracting(Member::getMemberName)
                .isEqualTo("memberName");
    }

    @Test
    void 회원가입_중복회원_예외발생() {
        //given
        MemberRequestDto member1 = createMemberDto("NAME1");
        MemberRequestDto member2 = createMemberDto("NAME1");
        memberService.createMember(member1);

        //when & then
        assertThrows(DuplicateMemberNameException.class,
                () -> memberService.createMember(member2),
                "이미 존재하는 회원명입니다.");
    }

    @Test
    void 전체회원_조회() {
        //given
        MemberRequestDto member1 = createMemberDto("NAME1");;
        Long savedId1 = memberService.createMember(member1);

        MemberRequestDto member2 = createMemberDto("NAME2");
        Long savedId2 = memberService.createMember(member2);

        //when
        List<MemberResponseDto> members = memberService.getAllMembers();

        //then
        assertThat(members)
                .hasSize(2)
                .extracting(MemberResponseDto::getMemberId)
                .containsExactlyInAnyOrder(savedId1, savedId2);

        assertThat(members)
                .extracting(MemberResponseDto::getMemberName)
                .containsExactlyInAnyOrder("NAME1", "NAME2");
    }

    // 추가 테스트 케이스
    @Test
    void 회원_단건_조회() {
        //given
        MemberRequestDto memberRequestDto = createMemberDto("user1");
        Long savedId = memberService.createMember(memberRequestDto);

        //when
        MemberResponseDto foundMember = memberService.getMemberById(savedId);

        //then
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getMemberId()).isEqualTo(savedId);
        assertThat(foundMember.getMemberName()).isEqualTo("user1");
    }

    private MemberRequestDto createMemberDto(String memberName) {
        return MemberRequestDto.builder()
                .memberName(memberName)
                .build();
    }
}