package com.hajimi.backend.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @Auther: 与或非
 * 2026/8/20 - 08 - 20
 * @Description: com.hajimi.backend.dto
 * @version: 1.0
 * 组员申请请假请求体
 */
@Data
public class LeaveApplyDto {
    private Integer memberId;   //请假的学员id
    private Integer type;       //请假类型：0病假1事假2其它
    private String reason;      //请假理由
    private String image;       //佐证图片（如病例、伤口图片等）
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;       //请假开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;         //请假结束时间
}
