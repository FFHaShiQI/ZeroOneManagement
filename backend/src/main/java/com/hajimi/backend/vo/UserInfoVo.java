package com.hajimi.backend.vo;

import lombok.Data;

@Data
public class UserInfoVo {
    /**
     * W
     * 对部分个人信息的封装
     */
    private Integer id;
    private String username;
    private String nickname;
    private String avatar;
    private Integer groupId;
}
