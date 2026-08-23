package com.hajimi.backend.service.impl;

import com.hajimi.backend.dto.RegisterDto;
import com.hajimi.backend.entity.Member;
import com.hajimi.backend.entity.Senior;
import com.hajimi.backend.mapper.MemberMapper;
import com.hajimi.backend.mapper.SeniorMapper;
import com.hajimi.backend.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hajimi.backend.utils.GlobalExceptionHandler.BizException;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private SeniorMapper seniorMapper;

    @Override
    public void register(RegisterDto dto) {
        if(dto.getUsername() == null
                || dto.getUsername().isEmpty()
                || dto.getPassword() == null
                || dto.getPassword().isEmpty()
                || dto.getOption() == null) {
            throw new BizException("必填参数不能为空");
        }
        //用户名重复检查(真实查询库表)
        if(memberMapper.countByUsername(dto.getUsername()) > 0
                || seniorMapper.countByUsername(dto.getUsername()) > 0) {
            throw new BizException("用户名不能重复");
        }
        if(Boolean.TRUE.equals(dto.getOption())){
            //学长注册
            if(dto.getInviteCode() == null || dto.getInviteCode().isBlank()){
                throw new BizException("学长注册必须填写邀请码");
            }
        }else if(Boolean.FALSE.equals(dto.getOption())){
            //学员注册
            if(dto.getGroupId() == null){
                throw new BizException("学员注册需要填写小组ID");
            }
        }

        //存学员数据(密码明文存储,与登录查询保持一致)
        if(Boolean.FALSE.equals(dto.getOption())){
            member.setUsername(dto.getUsername());
            member.setPassword(dto.getPassword());
            member.setGroupId(dto.getGroupId());
            memberMapper.saveInMember(member);
        }

        //存学长数据
        if(Boolean.TRUE.equals(dto.getOption())){
            senior.setUsername(dto.getUsername());
            senior.setPassword(dto.getPassword());
            seniorMapper.saveInSenior(senior);
        }
    }
}
