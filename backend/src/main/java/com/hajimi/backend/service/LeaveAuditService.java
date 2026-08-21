package com.hajimi.backend.service;

import com.hajimi.backend.dto.LeaveAuditDto;

public interface LeaveAuditService {
    /**
     * 通过前端传入封装在dto里的数据访问数据库
     * leaveAuditDto
     */
    void auditResult(LeaveAuditDto leaveAuditDto);
}
