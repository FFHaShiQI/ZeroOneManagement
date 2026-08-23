package com.hajimi.backend.dto;

import lombok.Data;

@Data
public class LoginDto {
    /**
     * W
     * 输入用户名密码，选择类型
     */
    private String username;
    private String password;
    private String role; //新增：member / senior
}
