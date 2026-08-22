package com.hajimi.backend.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Auther: 与或非
 * @Date: 2026/8/20 - 08 - 20
 * @Description: com.hajimi.backend.vo
 * @version: 1.0
 * 请假记录响应体
 */
@Data
public class LeaveVo {
    private Integer id;         //编号
    private Integer memberId;   //请假的学员id
    private Integer type;       //请假类型：0病假1事假2其它
    private String reason;      //请假理由
    private String image;       //佐证图片（如病例、伤口图片等）
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;    //请假开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;      //请假结束时间
    private Integer status;     //审核状态：0审核中1通过2驳回
    private String seniorName;  //审核者昵称（只有学长可以审核）
    private String feedback;    //审核意见
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;  //请假记录创建时间
}
