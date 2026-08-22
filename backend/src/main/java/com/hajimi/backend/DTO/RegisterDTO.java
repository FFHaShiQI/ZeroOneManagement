package com.hajimi.backend.DTO;

import lombok.Data;

@Data
public class RegisterDTO {
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
