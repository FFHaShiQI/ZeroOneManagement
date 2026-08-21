package com.hajimi.backend.entity;

import lombok.Data;

@Data
public class Group {
    private Integer id;
<<<<<<< HEAD
    private String name;        //小组名称
    private Integer language;   //小组类型
    private Integer groupSize;  //小组人数
    private String clockLink;   //小组打卡表链接
    private Integer seniorId;   //关联学长id
=======
    private String name;
    private Integer language;
    private String clockLink;
    private Integer groupSize;
    private Integer seniorId;
>>>>>>> feature/senior/leave-audit
}
