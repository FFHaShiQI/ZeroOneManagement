package com.hajimi.backend.entity;

import lombok.Data;

/**
 * @Auther: jone
 * @Date: 2026/8/19 - 08 - 19
 * @Description: com.hajimi.backend.entity
 * @version: 1.0
 * 组员实体，对应数据库表 Member
 */
@Data
public class Member {
    private Integer id;         //组员编号
    private String username;    //用户名（登录账号）
    private String password;    //密码（加密存储）
    private String name;        //昵称
    private Integer groupId;    //所属小组id
    private Integer gender;     //性别：1男2女
    private Integer age;        //年龄
    private String image;       //头像（链接）
    private String selfIntro;   //自我介绍
    private String number;      //联系方式
    private Integer pos;        //小组职位：1组长2副组长3产品经理4信息官5技术官6监督官
}
