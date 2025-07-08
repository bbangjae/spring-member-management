package com.example.spring_member_management.repository;

import com.example.spring_member_management.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository {
    private static final Map<Long, Member> memberStore = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Member save(Member member) {
        if (member.getMemberId() == null) {
            member = new Member(++sequence, member.getMemberName());
        }
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

    @Override
    public void deleteById(Long memberId) {
        memberStore.remove(memberId);
    }

    public void clearStore() {
        memberStore.clear();
    }
}
