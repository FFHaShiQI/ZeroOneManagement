package com.hajimi.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *  小组信息结构体
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    private Integer id;					// 小组编号
    private String name;				// 小组名
    private Integer language;			// 小组的语言类型：0表示C语言，1表示C++，2表示Java
    private String clockLink;			// 打卡表超链接
    private Integer groupSize;			// 小组规模（人数）
    private String seniorId;			// 关联学长ID

    // 额外封装小组中的组员信息
    private List<Member> members;
}
