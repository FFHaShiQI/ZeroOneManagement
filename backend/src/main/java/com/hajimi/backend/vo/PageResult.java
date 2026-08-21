package com.hajimi.backend.vo;

<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private Long total;
    private List<T> rows;
=======
import lombok.Data;

import java.util.List;

/**
 * @Auther: 与或非
 * @Date: 2026/8/20 - 08 - 20
 * @Description: com.hajimi.backend.vo
 * @version: 1.0
 * 分页结果通用类型
 */
@Data
public class PageResult<T> {
    private Long total;         //总记录数
    private List<T> rows;       //结果列表
>>>>>>> feature/member
}
