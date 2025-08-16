    package com.example.spring_member_management.dto;

    import com.example.spring_member_management.entity.Team;
    import lombok.*;

    import java.util.List;
    import java.util.stream.Collectors;

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class TeamDetailResponseDto {

        private Long teamId;
        private String teamName;
        private List<MemberSimpleDto> members;

        public static TeamDetailResponseDto of(Team team) {
            return TeamDetailResponseDto.builder()
                    .teamId(team.getId())
                    .teamName(team.getName())
                    .members(
                            team.getMembers().stream()
                            .map(MemberSimpleDto::of)
                            .collect(Collectors.toList())
                    )
                    .build();
        }
    }