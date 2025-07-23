package com.example.spring_member_management.mapper;

import com.example.spring_member_management.domain.Member;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    @Insert("INSERT INTO member(member_name) VALUES(#{memberName})")
    @Options(useGeneratedKeys = true, keyProperty = "memberId")
    void save(Member member);

    @Select("SELECT member_id, member_name FROM member WHERE member_id = #{memberId}")
    Optional<Member> findById(@Param("memberId") Long id);

    @Select("SELECT member_id, member_name FROM member WHERE member_name = #{memberName}")
    Optional<Member> findByName(@Param("memberName") String name);

    @Select("SELECT member_id, member_name FROM member")
    List<Member> findAll();

    @Update("UPDATE member SET member_name = #{memberName} WHERE member_id = #{memberId}")
    void updateNameById(@Param("memberName") String memberName, @Param("memberId")  Long memberId);

    @Delete("DELETE FROM member WHERE member_id = #{memberId}")
    void deleteById(@Param("memberId") Long memberId);
}