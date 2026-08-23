package com.hajimi.backend.dto;

import lombok.Data;

/**
 * @Auther: jone
 * @Date: 2026/8/19 - 08 - 19
 * @Description: com.hajimi.backend.dto
 * @version: 1.0
 * 学长添加组员请求体
 */
@Data
public class MemberAddDto {
    private String name;        //昵称
    private String username;    //用户名
    private String password;    //密码（默认123456）
    private Integer groupId;    //所属小组id
    private Integer gender;     //性别：1男2女（默认1）
    private Integer age;        //年龄
    private String image;       //头像（链接）
    private String number;      //联系方式
    private Integer pos;        //小组职位：1组长2副组长3产品经理4信息官5技术官6监督官
}
