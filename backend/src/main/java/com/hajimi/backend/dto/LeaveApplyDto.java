package com.hajimi.backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveApplyDto {
    /**
     * 结束时间
     */
    private LocalDate end;
    /**
     * 按学员昵称模糊搜索
     */
    private String name;
    /**
     * 开始时间
     */
    private LocalDate start;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 请假类型
     */
    private Integer type;
    /**
     * 页码
     */
    private Integer pageNum;
    /**
     * 页大小
     */
    private Integer pageSize;

}