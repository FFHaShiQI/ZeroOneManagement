package com.hajimi.backend.dto;

import lombok.Data;

/**
 * @Auther: jone
 * @Date: 2026/8/19 - 08 - 19
 * @Description: com.hajimi.backend.dto
 * @version: 1.0
 * 组员分页查询条件
 */
@Data
public class MemberQueryDto {
    private String name;        //昵称（可模糊查询）
    private Integer gender;     //性别：1男2女
    private Integer pos;        //职位
    private String groupName;   //小组名（可模糊查询）
    private Integer page;       //页码（默认1）
    private Integer pageSize;   //每页最大数据量（默认10）
}
