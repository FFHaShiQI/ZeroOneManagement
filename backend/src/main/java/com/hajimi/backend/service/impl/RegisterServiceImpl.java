package com.hajimi.backend.service.impl;

import com.hajimi.backend.dto.RegisterDto;
import com.hajimi.backend.entity.Member;
import com.hajimi.backend.entity.Senior;
import com.hajimi.backend.mapper.MemberMapper;
import com.hajimi.backend.mapper.SeniorMapper;
import com.hajimi.backend.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hajimi.backend.Utils.GlobalExceptionHandler.BizException;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private SeniorMapper seniorMapper;

    //固定学长邀请码
    private static final String SENIOR_INVITE_CODE = "STAR2026";

    /**
     * W
     * 注册（加密存入数据库，测试时不要用数据库的信息，自己用注册功能加一个）
     * dto
     */
    @Override
    public void register(RegisterDto dto) {
        if(dto.getUsername() == null
                || dto.getUsername().isEmpty()
                || dto.getPassword() == null
                || dto.getPassword().isEmpty()
                || dto.getOption() == null) {
            throw new BizException("必填参数不能为空");
        }

        // 查询数据库判断用户名是否重复
        Member existMember = memberMapper.getByUsername(dto.getUsername());
        Senior existSenior = seniorMapper.getByUsername(dto.getUsername());
        if(existMember != null || existSenior != null){
            throw new BizException("用户名不能重复");
        }

        if(Boolean.TRUE.equals(dto.getOption())){
            if(dto.getInviteCode() == null || dto.getInviteCode().isBlank() || !dto.getInviteCode().equals(SENIOR_INVITE_CODE)){
                throw new BizException("学长注册必须填写真确的邀请码");
            }
        }else if(Boolean.FALSE.equals(dto.getOption())){
            if(dto.getGroupId() == null){
                throw new BizException("学员注册需要填写小组ID");
            }
        }

        if(Boolean.FALSE.equals(dto.getOption())){
            Member member = new Member();

            member.setUsername(dto.getUsername());
            member.setPassword(dto.getPassword());
            member.setGroupId(dto.getGroupId());
            memberMapper.saveInMember(member);
        }

        if(Boolean.TRUE.equals(dto.getOption())){
            Senior senior = new Senior();
            senior.setUsername(dto.getUsername());
            senior.setPassword(dto.getPassword());
            seniorMapper.saveInSenior(senior);
        }
    }
}
