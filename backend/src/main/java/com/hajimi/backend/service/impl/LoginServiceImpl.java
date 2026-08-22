package com.hajimi.backend.service.impl;

import com.hajimi.backend.DTO.LoginDTO;
import com.hajimi.backend.Utils.JwtUtils;
import com.hajimi.backend.VO.LoginVO;
import com.hajimi.backend.VO.UserInfoVO;
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
    /**
     * W
     * 登录功能的实现
     */
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private SeniorMapper seniorMapper;

    public LoginVO login(LoginDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        String reqRole = dto.getRole();
        //判断数据库中是否有该学员，有就封装到对应的VO，没有就返回空
        if ("member".equals(reqRole)) {
            Member member = memberMapper.getByUsernameAndPwd(username, password);
            if (member == null) {
                return null;
            }
            LoginVO vo = new LoginVO();
            vo.setRole("member");

            Map<String, Object> claims = new HashMap<>();
            claims.put("id", member.getId());
            claims.put("username", member.getUsername());
            claims.put("role", "member");
            vo.setToken(JwtUtils.generateJwt(claims));

            UserInfoVO userInfo = new UserInfoVO();
            userInfo.setId(member.getId());
            userInfo.setUsername(member.getUsername());
            userInfo.setNickname(member.getName());
            userInfo.setAvatar(member.getImage());
            userInfo.setGroupId(member.getGroupId());
            vo.setUserInfo(userInfo);
            return vo;
            //判断数据库中是否有该学长，有就封装到对应的VO，没有就返回空
        } else if ("senior".equals(reqRole)) {
            Senior senior = seniorMapper.getByUsernameAndPwd(username, password);
            if (senior == null) {
                return null;
            }
            LoginVO vo = new LoginVO();
            vo.setRole("senior");

            Map<String, Object> claims = new HashMap<>();
            claims.put("id", senior.getId());
            claims.put("username", senior.getUsername());
            claims.put("role", "senior");
            vo.setToken(JwtUtils.generateJwt(claims));

            UserInfoVO userInfo = new UserInfoVO();
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
