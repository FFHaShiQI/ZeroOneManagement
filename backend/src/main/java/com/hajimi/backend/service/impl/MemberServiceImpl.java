package com.hajimi.backend.service.impl;

import com.hajimi.backend.dto.MemberAddDto;
import com.hajimi.backend.dto.MemberQueryDto;
import com.hajimi.backend.dto.MemberUpdateDto;
import com.hajimi.backend.entity.Group;
import com.hajimi.backend.entity.Member;
import com.hajimi.backend.mapper.GroupMapper;
import com.hajimi.backend.mapper.LeavesMapper;
import com.hajimi.backend.mapper.MemberMapper;
import com.hajimi.backend.service.MemberService;
import com.hajimi.backend.vo.GroupVo;
import com.hajimi.backend.vo.MemberVo;
import com.hajimi.backend.vo.PageResult;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private LeavesMapper leavesMapper;

    @Override
    public MemberVo getById(Integer id) {
        return memberMapper.getById(id);
    }

    @Override
    public void update(MemberUpdateDto dto) {
        memberMapper.update(dto);
    }

    @Override
    public PageResult<MemberVo> pageQuery(MemberQueryDto query) {
        //分页参数默认值
        if (query.getPage() == null || query.getPage() < 1) {
            query.setPage(1);
        }
        if (query.getPageSize() == null || query.getPageSize() < 1) {
            query.setPageSize(10);
        }
        int offset = (query.getPage() - 1) * query.getPageSize();
        List<MemberVo> rows = memberMapper.pageQuery(query, offset);
        Long total = memberMapper.count(query);
        return new PageResult<>(total, rows);
    }

    @Override
    public void add(MemberAddDto dto) {
        //用户名查重
        if (memberMapper.getByUsername(dto.getUsername()) != null) {
            throw new RuntimeException("用户名 '" + dto.getUsername() + "' 已被占用，请使用其他用户名");
        }
        Member member = new Member();
        BeanUtils.copyProperties(dto, member);
        //默认密码123456,与登录的BCrypt校验保持一致
        String rawPwd = member.getPassword() == null || member.getPassword().isEmpty()
                ? "123456" : member.getPassword();
        member.setPassword(BCrypt.hashpw(rawPwd, BCrypt.gensalt()));
        //默认性别:1男
        if (member.getGender() == null) {
            member.setGender(1);
        }
        memberMapper.insert(member);
    }



    @Override
    public void deleteByIds(String ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        if (!idList.isEmpty()) {
            // 检查每个要删除的学员是否有请假记录
            for (Integer id : idList) {
                Long leaveCount = leavesMapper.countPendingByMemberId(id);
                if (leaveCount != null && leaveCount > 0) {
                    // 错误提示
                    MemberVo member = memberMapper.getById(id);
                    String memberName = member != null ? member.getName() : "该学员";
                    throw new RuntimeException(memberName + "有请假记录，无法删除");
                }
            }
            for (Integer memberId : idList) {
                Integer groupId = memberMapper.getGroupIdById(memberId);
                Integer groupSize = groupMapper.getGroupSizeById(groupId);
                groupMapper.updateGroupSize(groupSize - 1, groupId);
            }
            memberMapper.deleteByIds(idList);
        }
    }


    @Override
    public GroupVo getGroup(Integer groupId) {
        List<MemberVo> members = memberMapper.getByGroupId(groupId);
        GroupVo gv = groupMapper.getById(groupId);
        gv.setMembers(members);
        return gv;
    }


}
