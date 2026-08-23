package com.hajimi.backend.entity;

import lombok.Data;

@Data
public class Member {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private Integer groupId;
    private Integer gender;
    private Integer age;
    private String image;
    private String selfIntro;
    private String number;
    private Integer pos;
}
