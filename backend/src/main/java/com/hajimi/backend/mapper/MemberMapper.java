package com.hajimi.backend.mapper;

import com.hajimi.backend.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    /**
     * W
     * 学员的数据库访问接口
     * username
     * password
     */
    Member getByUsernameAndPwd(String username, String password);

    void saveInMember(Member member);
}
