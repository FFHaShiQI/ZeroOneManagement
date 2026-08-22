package com.hajimi.backend.VO;

import lombok.Data;

@Data
public class LoginVO {
    /**
     * W
     * 登录的所有信息的封装
     */
    private String token;
    private String role;
    private UserInfoVO userInfo;
}
