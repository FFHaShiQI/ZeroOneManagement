package com.hajimi.backend.vo;

import lombok.Data;

/**
 * @Auther: jone
 * @Date: 2026/8/19 - 08 - 19
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
    private String start;       //请假开始时间
    private String end;         //请假结束时间
    private Integer status;     //审核状态：0审核中1通过2驳回
    private Integer seniorId;   //审核者id（只有学长可以审核）
    private String feedback;    //审核意见
    private String createTime;  //请假记录创建时间
}
