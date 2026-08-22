package com.hajimi.backend.entity;

import lombok.Data;

@Data
public class Member {
    private Integer id;
    private String username;
    private String password;
    private String name; //昵称 nickname
    private Integer groupId;
    private Integer gender;
    private Integer age;
    private String image; //avatar头像
    private String selfIntro;
    private String number;
    private String pos;
}
