package com.example.spring_member_management.service.jpa;

import com.example.spring_member_management.dto.MemberWithAddressRequestDto;
import com.example.spring_member_management.dto.MemberWithTeamResponseDto;
import com.example.spring_member_management.entity.Address;
import com.example.spring_member_management.entity.Member;
import com.example.spring_member_management.entity.Team;
import com.example.spring_member_management.exception.DuplicateMemberNameException;
import com.example.spring_member_management.repository.JpaMemberRepository;
import com.example.spring_member_management.repository.JpaTeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class JpaMemberServiceTest {

    @Autowired
    private JpaMemberService memberService;

    @Autowired
    private JpaTeamRepository teamRepository;

    @Autowired
    private JpaMemberRepository memberRepository;

    private Address defaultAddress;
    private Team defaultSavedTeam;

    @BeforeEach
    void setUp() {
        defaultAddress = Address.builder()
                .street("서울")
                .city("신림")
                .zipcode("1234")
                .build();

        Team defaultTeam = new Team("개발팀");
        defaultSavedTeam = teamRepository.save(defaultTeam);
    }

    @Test
    void 회원가입_팀지정_성공() {
        //given
        MemberWithAddressRequestDto dto = createDto("jae1", defaultSavedTeam.getId());

        //when
        Long savedId = memberService.createMember(dto);
        Optional<Member> foundMember = memberRepository.findById(savedId);

        //then
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get())
                .extracting("name", "team.id")
                .contains("jae1", defaultSavedTeam.getId());
    }

    @Test
    void 회원가입_팀미지정_성공() {
        //given
        MemberWithAddressRequestDto dto = createDto("jae", null);

        //when
        Long savedId = memberService.createMember(dto);
        Optional<Member> foundMember = memberRepository.findById(savedId);

        //then
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getName()).isEqualTo("jae");
        assertThat(foundMember.get().getTeam()).isNull();
    }

    @Test
    void 멤버_해당_팀_목록_조회_성공() {
        //given
        Team team2 = teamRepository.save(new Team("인사팀"));

        Member member1 = createMember("jae1", defaultSavedTeam);
        Member member2 = createMember("jae2", team2);
        Member member3 = createMember("jae3", null);

        memberRepository.saveAll(List.of(member1, member2, member3));

        //when
        List<MemberWithTeamResponseDto> members = memberService.getAllMembersWithTeam();

        //then
        assertThat(members)
                .hasSize(3)
                .extracting("memberName", "teamName")
                .containsExactlyInAnyOrder(
                        tuple("jae1", "개발팀"),
                        tuple("jae2", "인사팀"),
                        tuple("jae3", null)
                );
    }

    @Test
    void 회원가입_중복_회원명_예외발생() {
        // given
        MemberWithAddressRequestDto dto1 = createDto("jae1", defaultSavedTeam.getId());
        memberService.createMember(dto1);

        MemberWithAddressRequestDto dto2 = createDto("jae1", defaultSavedTeam.getId());

        // when & then
        assertThrows(
                DuplicateMemberNameException.class,
                () -> memberService.createMember(dto2)
        );
    }

    private MemberWithAddressRequestDto createDto(String name, Long teamId) {
        return MemberWithAddressRequestDto.builder()
                .memberName(name)
                .teamId(teamId)
                .address(MemberWithAddressRequestDto.AddressDto.builder()
                        .street(defaultAddress.getStreet())
                        .city(defaultAddress.getCity())
                        .zipcode(defaultAddress.getZipcode())
                        .build())
                .build();
    }

    private Member createMember(String name, Team team) {
        return Member.builder()
                .memberName(name)
                .address(defaultAddress)
                .team(team)
                .build();
    }
}