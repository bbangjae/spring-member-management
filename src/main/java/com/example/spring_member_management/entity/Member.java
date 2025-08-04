package com.example.spring_member_management.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @Setter(AccessLevel.PROTECTED)
    private Team team;

    @Embedded
    private Address address;


    @OneToOne
    @JoinColumn(name = "locker_id", unique = true)
    private Locker locker;

    public Member(String memberName, Address address) {
        this.name = memberName;
        this.address = address;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void assignLocker(Locker locker) {
        this.locker = locker;
    }
}
