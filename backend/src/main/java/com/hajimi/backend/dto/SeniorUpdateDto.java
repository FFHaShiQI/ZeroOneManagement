package com.hajimi.backend.dto;

import lombok.Data;

/**
 * @Auther: Cat
 * @Date: 2026/8/19 - 08 - 19
 * @Description: com.hajimi.backend.dto
 * @version: 1.0
 * 学长修改个人信息请求体
 */

@Data
public class SeniorUpdateDto {
    private Integer id;         //学长编号（必填）
    private String name;        //昵称
    private String number;      //联系方式
    private String image;       //头像
    private Integer type;       //大小学长：1大学长2小学长
    private String username;    //用户名
    private String password;    //密码
}
