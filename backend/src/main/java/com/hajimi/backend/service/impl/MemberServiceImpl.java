package com.hajimi.backend.service.impl;

import com.hajimi.backend.dto.MemberUpdateDto;
import com.hajimi.backend.entity.Group;
import com.hajimi.backend.entity.Member;
import com.hajimi.backend.mapper.GroupMapper;
import com.hajimi.backend.mapper.MemberMapper;
import com.hajimi.backend.service.MemberService;
import com.hajimi.backend.vo.GroupVo;
import com.hajimi.backend.vo.MemberVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 与或非
 * @Date: 2026/8/20 - 08 - 20
 * @Description: com.hajimi.backend.service.impl
 * @version: 1.0
 * 组员业务实现类
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public MemberVo getById(Integer id) {
        return memberMapper.getById(id);
    }

    @Override
    public void update(MemberUpdateDto dto) {
        memberMapper.update(dto);
    }

    @Override
    public GroupVo getGroup(Integer groupId) {
        List<MemberVo> members = memberMapper.getByGroupId(groupId);
        GroupVo gv = groupMapper.getById(groupId);
        gv.setMembers(members);
        return gv;
    }


}
