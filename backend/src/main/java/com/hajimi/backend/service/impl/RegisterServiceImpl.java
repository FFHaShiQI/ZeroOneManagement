package com.hajimi.backend.service.impl;

import com.hajimi.backend.dto.RegisterDto;
import com.hajimi.backend.entity.Member;
import com.hajimi.backend.entity.Senior;
import com.hajimi.backend.exception.globalExceptionHandler;
import com.hajimi.backend.mapper.MemberMapper;
import com.hajimi.backend.mapper.SeniorMapper;
import com.hajimi.backend.service.RegisterService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private SeniorMapper seniorMapper;

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
            throw new globalExceptionHandler.BizException("必填参数不能为空");
        }
<<<<<<< HEAD
        Member  member = new Member();
        Senior senior = new Senior();
        if(Objects.equals(member.getUsername(), dto.getUsername())
                || Objects.equals(senior.getUsername(), dto.getUsername())) {
            throw new globalExceptionHandler.BizException("用户名不能重复");
=======

        // 查询数据库判断用户名是否重复
        Member existMember = memberMapper.getByUsername(dto.getUsername());
        Senior existSenior = seniorMapper.getByUsername(dto.getUsername());
        if(existMember != null || existSenior != null){
            throw new BizException("用户名不能重复");
>>>>>>> ZeroOneManagement/feature/login
        }

        if(Boolean.TRUE.equals(dto.getOption())){
            if(dto.getInviteCode() == null || dto.getInviteCode().isBlank()){
                throw new globalExceptionHandler.BizException("学长注册必须填写邀请码");
            }
        }else if(Boolean.FALSE.equals(dto.getOption())){
            if(dto.getGroupId() == null){
                throw new globalExceptionHandler.BizException("学员注册需要填写小组ID");
            }
        }

        if(Boolean.FALSE.equals(dto.getOption())){
            Member member = new Member();
            String encryptPwd = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
            member.setUsername(dto.getUsername());
            member.setPassword(encryptPwd);
            member.setGroupId(dto.getGroupId());
            memberMapper.saveInMember(member);
        }

        if(Boolean.TRUE.equals(dto.getOption())){
            Senior senior = new Senior();
            String encryptPwd = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
            senior.setUsername(dto.getUsername());
            senior.setPassword(encryptPwd);
            seniorMapper.saveInSenior(senior);
        }
    }
}
