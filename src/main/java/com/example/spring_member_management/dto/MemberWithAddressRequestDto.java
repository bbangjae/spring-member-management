package com.example.spring_member_management.dto;

import com.example.spring_member_management.entity.Address;
import com.example.spring_member_management.entity.Member;
import com.example.spring_member_management.entity.Team;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberWithAddressRequestDto {

    private String memberName;
    private Long teamId;
    private AddressDto address;
    private String lockerNumber;

    public Member toEntity(Team team) {
        return Member.builder()
                .memberName(this.memberName)
                .address(this.address.toAddressEntity())
                .team(team)
                .build();
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AddressDto {
        private String street;
        private String city;
        private String zipcode;

        public Address toAddressEntity() {
            return Address.builder()
                    .street(this.street)
                    .city(this.city)
                    .zipcode(this.zipcode)
                    .build();
        }
    }
}