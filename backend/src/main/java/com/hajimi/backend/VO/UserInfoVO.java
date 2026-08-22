package com.hajimi.backend.VO;

import lombok.Data;

@Data
public class UserInfoVO {
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
