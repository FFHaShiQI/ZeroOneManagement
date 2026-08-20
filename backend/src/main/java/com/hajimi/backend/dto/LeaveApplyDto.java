package com.hajimi.backend.dto;

import lombok.Data;

/**
 * @Auther: jone
 * @Date: 2026/8/19 - 08 - 19
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
    private String start;       //请假开始时间
    private String end;         //请假结束时间
}
