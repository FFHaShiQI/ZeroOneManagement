package com.hajimi.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *  请假申请结构体
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveApply {
    private Integer id;					// 编号
    private Integer memberId;			// 请假的学员id
    private String reason;				// 请假理由
    private Integer type;				// 请假类型（0病假，1事假，2其它）
    private String image;				// 佐证图片（如病例、伤口图片等）
    private LocalDate start;			// 请假开始时间
    private LocalDate end;				// 请假结束时间
    private LocalDateTime createDataTime; //请假申请创建时间
    private Integer status = 0;			// 审核状态（默认为0）0表示审核中，1表示审核成功，2表示审核失败
    private Integer seniorId;			// 审核者id（只有学长可以审核）
    private String feedback;    		// 反馈信息
}
