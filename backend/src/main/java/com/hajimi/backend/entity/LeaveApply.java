package com.hajimi.backend.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LeaveApply {
    private Integer id;
    private Integer memberId;
    private String reason;
    private Integer type;
    private String image;
    private LocalDate start;
    private LocalDate end;
    private Integer status;
    private Integer seniorId;
    private String feedback;
    @DateTimeFormat(pattern = "yyyy-MM-dd MM:mm:ss")
    private LocalDateTime createTime;
}
