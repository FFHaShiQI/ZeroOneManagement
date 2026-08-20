package com.hajimi.backend.service.impl;

import com.hajimi.backend.mapper.GroupMapper;
import com.hajimi.backend.mapper.MemberMapper;
import com.hajimi.backend.pojo.Group;
import com.hajimi.backend.pojo.Member;
import com.hajimi.backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private GroupMapper groupMapper;

    // 查询组员个人信息
    @Override
    public Member selectMember(Integer id) {
        return memberMapper.selectByID(id);
    }

    // 修改组员个人信息
    @Override
    public void updateMember(Member member) {
        memberMapper.update(member);
    }

    // 查询个人所在小组信息
    @Override
    public Group selectGroup(Integer groupId) {
        List<Member> members = memberMapper.selectByGroupID(groupId);
        Group group = groupMapper.selectByID(groupId);
        group.setMembers(members);
        return group;
    }
}
