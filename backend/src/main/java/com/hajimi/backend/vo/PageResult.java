package com.hajimi.backend.vo;

import lombok.Data;

import java.util.List;

/**
 * @Auther: jone
 * @Date: 2026/8/19 - 08 - 19
 * @Description: com.hajimi.backend.vo
 * @version: 1.0
 * 分页结果通用类型
 */
@Data
public class PageResult<T> {
    private Long total;         //总记录数
    private List<T> rows;       //结果列表
}
