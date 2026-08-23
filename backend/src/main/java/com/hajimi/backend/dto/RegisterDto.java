package com.hajimi.backend.dto;

import lombok.Data;

@Data
public class RegisterDto {
    /**
     * W
     * 对注册输入参数的封装
     */
    private String username;
    private String password;
    private String inviteCode;
    private Integer groupId;
    private Boolean option;
}
