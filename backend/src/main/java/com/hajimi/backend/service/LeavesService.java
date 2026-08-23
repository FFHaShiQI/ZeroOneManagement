package com.hajimi.backend.service;

import com.hajimi.backend.dto.LeaveApplyDto;
import com.hajimi.backend.vo.PageResult;
import com.hajimi.backend.vo.LeaveApplyVo;

public interface LeavesService {
    /**
     * W
     * 用封装的dto访问数据库
     * leaveApplyDto
     */
    PageResult<LeaveApplyVo> fuzzyQuery(LeaveApplyDto leaveApplyDto);

}
