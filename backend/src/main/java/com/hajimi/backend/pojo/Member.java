package com.hajimi.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *   组员信息结构体
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private Integer id;					// 组员编号
    private String username;			// 用户名 --2551564155
    private String password;			// 密码
    private String name;				// 昵称 --土豆雷
    private Integer groupId ;			// 所属小组的id：
    private Integer gender;				// 性别：1男2女
    private Integer age;				// 年龄
    private String image;				// 头像（链接）
    private String selfIntro;			// 自我介绍
    private String number;      		// 联系方式
    private Integer pos;				// 小组职位
}
