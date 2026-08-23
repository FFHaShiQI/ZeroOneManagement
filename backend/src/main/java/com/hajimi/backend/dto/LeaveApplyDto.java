package com.hajimi.backend.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class LeaveApplyDto {
    /** 请假的学员id */
    private Integer memberId;
    /** 请假类型：0病假 1事假 2其它 */
    private Integer type;
    /** 请假理由 */
    private String reason;
    /** 佐证图片（如病例、伤口图片等） */
    private String image;
    /** 请假开始时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;
    /** 请假结束时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;
    /** 按学员昵称模糊搜索 */
    private String name;
    /** 审核状态 */
    private Integer status;
    /** 页码(与前端传参 page 对齐) */
    private Integer page;
    /** 页大小 */
    private Integer pageSize;
}
