package com.example.spring_member_management.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private Team team;

    @Embedded
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "locker_id", unique = true)
    private Locker locker;

    @Builder
    public Member(String memberName, Address address, Team team) {
        this.name = memberName;
        this.address = address;
        if (team != null) {
            changeTeam(team);
        }
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeTeam(Team team) {
        if (this.team != null) {
            this.team.getMembers().remove(this);
        }

        this.team = team;
        if (team != null) {
            team.getMembers().add(this);
        }
    }

    public void assignLocker(Locker locker) {
        this.locker = locker;
    }
}
