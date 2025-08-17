package com.example.spring_member_management.dto;

import com.example.spring_member_management.entity.Address;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUpdateRequestDto {

    private Long memberId;
    private String memberName;
    private Long teamId;
    private Address address;
    private String lockerNumber;
}