package com.hajimi.backend.vo;

import lombok.Data;

/**
 * @Auther: 与或非
 * @Date: 2026/8/20 - 08 - 20
 * @Description: com.hajimi.backend.vo
 * @version: 1.0
 * 组员信息响应体
 */
@Data
public class MemberVo {
    private Integer id;         //组员编号
    private String name;        //昵称
    private String username;    //用户名
    private String password;    //密码
    private Integer groupId;    //所属小组id
    private String groupName;   //所属小组名称
    private Integer gender;     //性别：1男2女
    private Integer age;        //年龄
    private String image;       //头像（链接）
    private String selfIntro;   //自我介绍
    private String number;      //联系方式
    private Integer pos;        //小组职位：1组长2副组长3产品经理4信息官5技术官6监督官
}
