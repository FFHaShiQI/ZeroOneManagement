package com.hajimi.backend.controller.impl;

import com.hajimi.backend.common.Result;
import com.hajimi.backend.dto.LeaveApplyDto;
import com.hajimi.backend.dto.LeaveAuditDto;
import com.hajimi.backend.entity.PageResult;
import com.hajimi.backend.service.LeaveAuditService;
import com.hajimi.backend.service.LeaveDeleteService;
import com.hajimi.backend.service.LeavesService;
import com.hajimi.backend.vo.LeaveApplyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping
public class LeavesController {

    @Autowired
    private LeavesService leavesService;
    @Autowired
    private LeaveAuditService leaveAuditService;
    @Autowired
    private LeaveDeleteService leaveDeleteService;

    /**
     * W
     * 分页模糊查询请假记录
     * leaveApplyDto
     */
    @GetMapping("/senior/leaves")
    public Result<PageResult<LeaveApplyVo>> leavesPageResult(LeaveApplyDto leaveApplyDto) {
        try {
            PageResult<LeaveApplyVo> pageResult = leavesService.fuzzyQuery(leaveApplyDto);
            return Result.success(pageResult);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * W
     * 修改审核状态和反馈结果
     * leaveAuditDto
     */
    @PutMapping("/senior/leaves")
    public Result<Void> leaveAudit(@RequestBody LeaveAuditDto leaveAuditDto) {
        try {
            leaveAuditService.auditResult(leaveAuditDto);
            return Result.success();
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * W
     * 通过id删除请假记录
     * id
     */
    @DeleteMapping("/senior/leaves/{id}")
    public Result<Void> leaveDelete(@PathVariable Integer id) {
        try {
            leaveDeleteService.deleteLeaveById(id);
            return Result.success();
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}
