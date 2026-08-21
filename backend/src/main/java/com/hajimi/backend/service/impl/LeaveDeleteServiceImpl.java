package com.hajimi.backend.service.impl;

import com.hajimi.backend.mapper.LeavesMapper;
import com.hajimi.backend.service.LeaveDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveDeleteServiceImpl implements LeaveDeleteService {

    @Autowired
    private LeavesMapper leavesMapper;

    @Override
    public void deleteLeaveById(Integer id) {
        /**
         * W
         * 删除的逻辑层
         */
        leavesMapper.deleteLeaveById(id);
    }
}
