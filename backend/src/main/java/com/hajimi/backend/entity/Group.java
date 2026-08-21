package com.hajimi.backend.entity;

import lombok.Data;

/**
 * @Auther: 与或非
 * @Date: 2026/8/20 - 08 - 20
 * @Description: com.hajimi.backend.entity
 * @version: 1.0
 * 小组实体，对应数据库表 Group
 */
@Data
public class Group {
    private Integer id;         //小组编号
    private String name;        //小组名
    private Integer language;   //小组语言类型：0C 1C++ 2Java
    private String clockLink;   //打卡表超链接
    private Integer groupSize;  //小组规模（人数）
    private Integer seniorId;   //关联学长id
}
