package com.example.spring_member_management.service.mybatis;

import com.example.spring_member_management.domain.Member;
import com.example.spring_member_management.dto.MemberRequestDto;
import com.example.spring_member_management.dto.MemberResponseDto;
import com.example.spring_member_management.exception.DuplicateMemberNameException;
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

    private MemberRequestDto createMemberDto(String memberName) {
        return MemberRequestDto.builder()
                .memberName(memberName)
                .build();
    }
}