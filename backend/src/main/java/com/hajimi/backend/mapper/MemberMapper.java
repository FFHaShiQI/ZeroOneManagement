package com.hajimi.backend.mapper;

import com.hajimi.backend.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    Member getByUsername(String username);

    void saveInMember(Member member);
}

