package com.hajimi.backend.service.impl;

import com.hajimi.backend.DTO.RegisterDTO;
import com.hajimi.backend.entity.Member;
import com.hajimi.backend.entity.Senior;
import com.hajimi.backend.mapper.MemberMapper;
import com.hajimi.backend.mapper.SeniorMapper;
import com.hajimi.backend.service.RegisterService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hajimi.backend.Utils.GlobalExceptionHandler.BizException;

import java.util.Objects;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private SeniorMapper seniorMapper;

    @Override
    public void register(RegisterDTO dto) {
        if(dto.getUsername() == null
                || dto.getUsername().isEmpty()
                || dto.getPassword() == null
                || dto.getPassword().isEmpty()
                || dto.getOption() == null) {
            throw new BizException("必填参数不能为空");
        }
        Member  member = new Member();
        Senior senior = new Senior();
        if(Objects.equals(member.getUsername(), dto.getUsername())
                || Objects.equals(senior.getUsername(), dto.getUsername())) {
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

        //存学员数据
        if(Boolean.FALSE.equals(dto.getOption())){
            String encryptPwd = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
            member.setUsername(dto.getUsername());
            member.setPassword(encryptPwd);
            member.setGroupId(dto.getGroupId());
            memberMapper.saveInMember(member);
        }

        //存学长数据
        if(Boolean.TRUE.equals(dto.getOption())){
            String encryptPwd = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
            senior.setUsername(dto.getUsername());
            senior.setPassword(encryptPwd);
            seniorMapper.saveInSenior(senior);
        }
    }
}
