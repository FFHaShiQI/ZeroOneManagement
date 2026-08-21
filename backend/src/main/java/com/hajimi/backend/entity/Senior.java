package com.hajimi.backend.entity;

import lombok.Data;

<<<<<<< HEAD
@Data
public class Senior {
    private Integer id;
    private Integer type;
    private String name;
    private String number;
    private String username;
    private String password;
    private String image;
=======
/**
 * @Auther: Cat
 * @Date: 2026/8/19 - 08 - 19
 * @Description: com.hajimi.backend.entity
 * @version: 1.0
 * 学长实体，对应数据库表 Senior
 */
@Data
public class Senior {
    private Integer id;         //学长编号
    private Integer type;       //学长类型：1大学长2小学长
    private String name;        //学长昵称
    private String number;      //联系方式
    private String username;    //用户名（登录账号）
    private String password;    //密码（加密存储）
    private String image;       //头像（链接）
>>>>>>> feature/senior/profile
}
