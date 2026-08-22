package com.hajimi.backend.DTO;

import lombok.Data;

@Data
public class LoginDTO {
    /**
     * W
     * 输入用户名密码，选择类型
     */
    private String username;
    private String password;
    private String role; //新增：member / senior
}
