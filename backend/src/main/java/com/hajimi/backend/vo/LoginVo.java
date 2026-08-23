package com.hajimi.backend.vo;

import lombok.Data;

@Data
public class LoginVo {
    /**
     * W
     * 登录的所有信息的封装
     */
    private String token;
    private String role;
    private UserInfoVo userInfo;
}
