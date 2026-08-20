package com.hajimi.backend.service;

import com.hajimi.backend.pojo.Group;
import com.hajimi.backend.pojo.Member;

public interface MemberService {

    // 查询组员个人信息
    Member selectMember(Integer id);

    // 修改组员个人信息
    void updateMember(Member member);

    // 查询个人所在小组信息
    Group selectGroup(Integer groupId);
}
