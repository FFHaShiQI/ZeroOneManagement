package com.hajimi.backend.vo;

import lombok.Data;

/**
 * @Auther: Cat
 * @Date: 2026/8/19 - 08 - 19
 * @Description: com.hajimi.backend.vo
 * @version: 1.0
 * 学长信息响应体
 */
@Data
public class SeniorVo {
    private Integer id;         //学长编号
    private String name;        //昵称
    private Integer type;       //大小学长：1大学长2小学长
    private String username;    //用户名
    private String password;    //密码
    private String number;      //联系方式
    private String image;       //头像
}
