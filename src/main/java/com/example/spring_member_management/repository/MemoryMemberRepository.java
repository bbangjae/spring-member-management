package com.example.spring_member_management.repository;

import com.example.spring_member_management.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {
    private static final Map<Long, Member> memberStore = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setMemberId(++sequence);
        memberStore.put(member.getMemberId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(memberStore.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return memberStore.values().stream()
                .filter(member -> member.getMemberName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(memberStore.values());
    }

    public void clearStore() {
        memberStore.clear();
    }
}
