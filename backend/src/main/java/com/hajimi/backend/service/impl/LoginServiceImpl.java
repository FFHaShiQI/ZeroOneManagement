package com.hajimi.backend.service.impl;

import com.hajimi.backend.dto.LoginDto;
import com.hajimi.backend.Utils.JwtUtils;
import com.hajimi.backend.vo.LoginVo;
import com.hajimi.backend.vo.UserInfoVo;
import com.hajimi.backend.entity.Member;
import com.hajimi.backend.entity.Senior;
import com.hajimi.backend.mapper.MemberMapper;
import com.hajimi.backend.mapper.SeniorMapper;
import com.hajimi.backend.service.LoginService;
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
     * 登录
     * dto
     */
    @Override
    public LoginVo login(LoginDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        String reqRole = dto.getRole();

        // 对用户名进行判空检验
        if ("member".equals(reqRole)) {
            Member member = memberMapper.getByUsername(username);
            if (member == null) {
                return null;
            }
            // 直接明文比对密码
            if (!password.equals(member.getPassword())) {
                return null;
            }

            // 将所有信息封装在LoginVo里面
            LoginVo vo = new LoginVo();
            vo.setRole("member");
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", member.getId());
            claims.put("username", member.getUsername());
            claims.put("role", "member");
            vo.setToken(JwtUtils.generateJwt(claims));

            UserInfoVo userInfo = new UserInfoVo();
            userInfo.setId(member.getId());
            userInfo.setUsername(member.getUsername());
            userInfo.setNickname(member.getName());
            userInfo.setAvatar(member.getImage());
            userInfo.setGroupId(member.getGroupId());
            vo.setUserInfo(userInfo);
            return vo;

        } else if ("senior".equals(reqRole)) {
            Senior senior = seniorMapper.getByUsername(username);
            if (senior == null) {
                return null;
            }
            // 直接明文比对密码
            if (!password.equals(senior.getPassword())) {
                return null;
            }

            LoginVo vo = new LoginVo();
            vo.setRole("senior");
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", senior.getId());
            claims.put("username", senior.getUsername());
            claims.put("role", "senior");
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
