package com.hajimi.backend.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LeaveApplyVo {
    //来自Member表
    private Integer id;         //请假记录id
    private String memberName;
    //来自Group表
    private String groupName;

    //来自LeaveApply表
    private Integer type;
    private String reason;
    private String image;
    private LocalDate start;
    private LocalDate end;
    private Integer status;
    private Integer seniorId;
    private String feedback;
    private LocalDateTime createTime;
}
