package com.example.spring_member_management.service.jpa;

import com.example.spring_member_management.dto.MemberWithAddressRequestDto;
import com.example.spring_member_management.dto.MemberDetailResponseDto;
import com.example.spring_member_management.dto.MemberWithTeamResponseDto;
import com.example.spring_member_management.entity.Locker;
import com.example.spring_member_management.entity.Member;
import com.example.spring_member_management.entity.Team;
import com.example.spring_member_management.exception.BaseResponseCode;
import com.example.spring_member_management.exception.DuplicateMemberNameException;
import com.example.spring_member_management.exception.MemberNotFoundException;
import com.example.spring_member_management.exception.TeamNotFoundException;
import com.example.spring_member_management.repository.JpaLockerRepository;
import com.example.spring_member_management.repository.JpaMemberRepository;
import com.example.spring_member_management.repository.JpaTeamRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaMemberService {

    private final JpaTeamRepository teamRepository;
    private final JpaLockerRepository lockerRepository;
    private final JpaMemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Long createMember(MemberWithAddressRequestDto memberRequestDto) {
        Team team = null;
        Long teamId = memberRequestDto.getTeamId();

        if (teamId != null) {
            team = findTeamById(teamId);
        }

        validateUniqueMemberName(memberRequestDto.getMemberName());

        Member member = memberRequestDto.toEntity(team);

        String lockerNumber = memberRequestDto.getLockerNumber();

        if (StringUtils.isNotBlank(lockerNumber)) {
            validateUniqueLockerNumber(lockerNumber);

            Locker locker = Locker.builder()
                    .lockerNumber(lockerNumber)
                    .build();

            member.assignLocker(locker);
        }

        return memberRepository.save(member).getId();
    }

    /**
     * 전체 회원 목록(팀 포함) 조회
     */
    public List<MemberWithTeamResponseDto> getAllMembersWithTeam() {
        return memberRepository.findAllWithTeam().stream()
                .map(MemberWithTeamResponseDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 회원 상세 정보를 조회
     */
    public MemberDetailResponseDto getMemberDetailById(Long memberId) {
        return memberRepository.findMemberDetailById(memberId)
                .map(MemberDetailResponseDto::of)
                .orElseThrow(() -> new MemberNotFoundException(BaseResponseCode.DATA_NOT_FOUND));
    }

    /**
     * 회원 이름 변경
     */
    @Transactional
    public void updateMemberNameById(Long memberId, String newName) {
        Member member = findMemberById(memberId);

        if (!member.getName().equals(newName))
            validateUniqueMemberName(newName);

        member.changeName(newName);
    }

    /**
     * 회원 삭제
     */
    @Transactional
    public void deleteMemberById(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    private void validateUniqueMemberName(String memberName) {
        if (memberRepository.existsByName(memberName)) {
            throw new DuplicateMemberNameException(BaseResponseCode.DUPLICATE_MEMBER_NAME);
        }
    }

    private void validateUniqueLockerNumber(String lockerNumber) {
        if (lockerRepository.existsByNumber(lockerNumber)) {
            throw new DuplicateMemberNameException(BaseResponseCode.DUPLICATE_LOCKER_NUMBER);
        }
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(BaseResponseCode.DATA_NOT_FOUND));
    }

    private Team findTeamById(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(BaseResponseCode.DATA_NOT_FOUND));
    }
}