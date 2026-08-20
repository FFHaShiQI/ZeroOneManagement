package com.hajimi.backend.vo;

import lombok.Data;

import java.util.List;

/**
 * @Auther: jone
 * @Date: 2026/8/19 - 08 - 19
 * @Description: com.hajimi.backend.vo
 * @version: 1.0
 * 小组信息响应体（含成员列表）
 */
@Data
public class GroupVo {
    private Integer id;             //小组编号
    private String name;            //小组名
    private Integer language;       //小组语言类型：0C 1C++ 2Java
    private String clockLink;       //打卡表超链接
    private Integer groupSize;      //小组规模（人数）
    private Integer seniorId;       //小学长id（学长表中的）
    private List<MemberVo> members; //组员列表
}
