package com.hajimi.backend.dto;

import lombok.Data;

/**
 * @Auther: 与或非
 * @Date: 2026/8/20 - 08 - 20
 * @Description: com.hajimi.backend.dto
 * @version: 1.0
 * 修改组员信息请求体（学长端/组员端共用）
 */
@Data
public class MemberUpdateDto {
    private Integer id;         //组员编号（必填）
    private String name;        //昵称
    private String password;    //密码
    private Integer gender;     //性别：1男2女
    private Integer age;        //年龄
    private String image;       //头像（链接）
    private String number;      //联系方式
    private String selfIntro;   //自我介绍
}
