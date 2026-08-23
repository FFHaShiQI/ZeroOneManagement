package com.hajimi.backend.dto;

import lombok.Data;

@Data
public class GroupQueryDto {//小组模糊分页查询
    private String groupName;
    private Integer groupType;
    private String senior;
    private Integer page;
    private Integer pageSize;
}
