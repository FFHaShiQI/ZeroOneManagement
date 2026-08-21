package com.hajimi.backend.service.impl;

import com.hajimi.backend.dto.LeaveAuditDto;
import com.hajimi.backend.mapper.LeavesMapper;
import com.hajimi.backend.service.LeaveAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveAuditServiceImpl implements LeaveAuditService {

    @Autowired
    private LeavesMapper leavesMapper;

    @Override
    public void auditResult(LeaveAuditDto leaveAuditDto) {
        /**
         * W
         * 审核的逻辑层
         */
        leavesMapper.audit(leaveAuditDto);
    }
}
