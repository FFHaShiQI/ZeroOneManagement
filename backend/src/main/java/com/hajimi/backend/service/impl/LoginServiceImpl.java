package com.hajimi.backend.service.impl;

import com.hajimi.backend.dto.LoginDto;
import com.hajimi.backend.utils.JwtUtils;
import com.hajimi.backend.vo.LoginVo;
import com.hajimi.backend.vo.UserInfoVo;
import com.hajimi.backend.entity.Member;
import com.hajimi.backend.entity.Senior;
import com.hajimi.backend.mapper.MemberMapper;
import com.hajimi.backend.mapper.SeniorMapper;
import com.hajimi.backend.service.LoginService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private SeniorMapper seniorMapper;

    /**
     * W
     * 登录（加密判断）
     * dto
     */
    @Override
    public LoginVo login(LoginDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        String reqRole = dto.getRole();
        //判断数据库中是否有该学员，有就封装到对应的VO，没有就返回空
        //适配:前端固定传 MEMBER/SENIOR 大写,统一忽略大小写判断并返回小写
        if ("MEMBER".equalsIgnoreCase(reqRole)) {
            Member member = memberMapper.getByUsername(username);
            if (member == null) {
                return null;
            }
            // 数据库password必须为BCrypt密文$2b$开头
            if (!BCrypt.checkpw(password, member.getPassword())) {
                return null;
            }

            // 将所有信息封装在LoginVo里面
            LoginVo vo = new LoginVo();
            vo.setRole("member");
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", member.getId());
            claims.put("username", member.getUsername());
            claims.put("role", "MEMBER");
            vo.setToken(JwtUtils.generateJwt(claims));

            UserInfoVo userInfo = new UserInfoVo();
            userInfo.setId(member.getId());
            userInfo.setUsername(member.getUsername());
            userInfo.setNickname(member.getName());
            userInfo.setAvatar(member.getImage());
            userInfo.setGroupId(member.getGroupId());
            vo.setUserInfo(userInfo);
            return vo;
            //判断数据库中是否有该学长，有就封装到对应的VO，没有就返回空
        } else if ("SENIOR".equalsIgnoreCase(reqRole)) {
            Senior senior = seniorMapper.getByUsername(username);
            if (senior == null) {
                return null;
            }
            // 数据库password必须为BCrypt密文$2b$开头
            if (!BCrypt.checkpw(password, senior.getPassword())) {
                return null;
            }

            LoginVo vo = new LoginVo();
            vo.setRole("senior");
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", senior.getId());
            claims.put("username", senior.getUsername());
            claims.put("role", "SENIOR");
            vo.setToken(JwtUtils.generateJwt(claims));

            UserInfoVo userInfo = new UserInfoVo();
            userInfo.setId(senior.getId());
            userInfo.setUsername(senior.getUsername());
            userInfo.setNickname(senior.getName());
            userInfo.setAvatar(senior.getImage());
            userInfo.setGroupId(null);
            vo.setUserInfo(userInfo);
            return vo;
        }
        return null;
    }
}
