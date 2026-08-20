package com.hajimi.backend.mapper;

import com.hajimi.backend.pojo.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberMapper {

    // 根据id查询组员信息
    @Select("select id,name,group_id,gender,age,image,self_intro,number,pos " +
            "from member where id = #{id}")
    Member selectByID(Integer id);

    // 修改组员信息
    void update(Member member);

    // 根据groupId查询组员信息
    @Select("select id,name,gender,age,image,number,pos " +
            "from member where group_id = #{groupId}")
    List<Member> selectByGroupID(Integer groupId);
}
