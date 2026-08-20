package com.hajimi.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  学长信息结构体
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Senior {
    private Integer id;					// 学长编号
    private Integer type;				// 学长类型：1表示大学长，2表示小学长
    private String name;				// 学长昵称
    private String number;				// 联系方式
    private String username;			// 用户名
    private String password;			// 密码
    private String image;				// 头像（链接）
}
