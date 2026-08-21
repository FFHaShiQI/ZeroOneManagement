package com.hajimi.backend.dto;

import lombok.Data;

@Data
public class LeaveAuditDto {
    /**
     * 请假记录id
     */
    private Integer id;
    /**
     * 审核状态 1同意 2驳回
     */
    private Integer status;
    /**
     * 审核意见
     */
    private String feedback;
}
