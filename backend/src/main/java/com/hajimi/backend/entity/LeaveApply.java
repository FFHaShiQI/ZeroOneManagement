package com.hajimi.backend.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LeaveApply {
    private Integer id;
    private Integer memberId;
    private String reason;
    private Integer type;
    private String image;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer status;
    private Integer seniorId;
    private String feedback;
    private LocalDateTime createTime;
}
