package com.example.spring_member_management.service.mybatis;

import com.example.spring_member_management.domain.Member;
import com.example.spring_member_management.dto.MemberRequestDto;
import com.example.spring_member_management.dto.MemberResponseDto;
import com.example.spring_member_management.exception.DuplicateMemberNameException;
import com.example.spring_member_management.exception.MemberNotFoundException;
import com.example.spring_member_management.mapper.MemberMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberMyBatisServiceTest {

    @Autowired MemberMyBatisService memberMyBatisService;
    @Autowired MemberMapper memberMapper;

    @Test
    void 회원가입_성공() {
        //given
        MemberRequestDto memberDto = createMemberDto("memberName");

        //when
        Long savedMemberId = memberMyBatisService.createMember(memberDto);
        Optional<Member> foundMember = memberMapper.findById(savedMemberId);

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
        MemberRequestDto member1 = createMemberDto("NAME1");;
        MemberRequestDto member2 = createMemberDto("NAME1");
        memberMyBatisService.createMember(member1);

        //when & then
        assertThrows(DuplicateMemberNameException.class,
                () -> memberMyBatisService.createMember(member2),
                "이미 존재하는 회원명입니다.");
    }

    @Test
    void 전체회원_조회() {
        //given
        MemberRequestDto member1 = createMemberDto("NAME1");;
        Long savedId1 =memberMyBatisService.createMember(member1);

        MemberRequestDto member2 = createMemberDto("NAME2");
        Long savedId2 = memberMyBatisService.createMember(member2);

        //when
        List<MemberResponseDto> members = memberMyBatisService.getAllMembers();

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
        MemberRequestDto memberDto = createMemberDto("NAME1");
        Long savedId = memberMyBatisService.createMember(memberDto);

        //when
        MemberResponseDto foundMember = memberMyBatisService.getMemberById(savedId);

        //then
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getMemberName()).isEqualTo("NAME1");
        assertThat(foundMember.getMemberId()).isEqualTo(savedId);
    }

    @Test
    void 회원이름_업데이트_성공() {
        //given
        MemberRequestDto memberDto = createMemberDto("NAME1");
        Long savedId = memberMyBatisService.createMember(memberDto);

        //when
        memberMyBatisService.updateMemberNameById(savedId, "NAME2");

        //then
        Member updatedMember = memberMapper.findById(savedId).orElseThrow();
        assertThat(updatedMember.getMemberName()).isEqualTo("NAME2");
    }

    @Test
    void 회원이름_업데이트_실패_동일이름() {
        //given
        MemberRequestDto memberDto = createMemberDto("NAME1");
        MemberRequestDto memberDto2 = createMemberDto("NAME2");
        Long savedId = memberMyBatisService.createMember(memberDto);
        memberMyBatisService.createMember(memberDto2);

        //when & then
        assertThrows(DuplicateMemberNameException.class,
                () -> memberMyBatisService.updateMemberNameById(savedId, "NAME2"),
                "이미 존재하는 회원명입니다.");
    }

    @Test
    void 회원이름_삭제_성공() {
        //given
        MemberRequestDto memberDto = createMemberDto("NAME1");
        Long savedId = memberMyBatisService.createMember(memberDto);

        //when
        memberMyBatisService.deleteMemberById(savedId);

        //then
        Optional<Member> deletedMember = memberMapper.findById(savedId);
        assertThat(deletedMember).isEmpty();
    }

    @Test
    void 회원이름_삭제_실패_존재하지않는_ID() {
        //given
        MemberRequestDto memberDto = createMemberDto("NAME1");
        Long savedId = memberMyBatisService.createMember(memberDto);

        //when & then
        assertThrows(MemberNotFoundException.class,
                () -> memberMyBatisService.deleteMemberById(savedId + 1L),
                "데이터 없음"
        );
    }

    private MemberRequestDto createMemberDto(String memberName) {
        return MemberRequestDto.builder()
                .memberName(memberName)
                .build();
    }
}