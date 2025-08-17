package com.example.spring_member_management.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Locker extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locker_id")
    private Long id;

    @Column(name = "locker_number", nullable = false)
    private String number;

    @Builder
    public Locker(String lockerNumber) {
        this.number = lockerNumber;
    }
}
