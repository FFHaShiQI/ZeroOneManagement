package com.hajimi.backend.service.impl;

import com.hajimi.backend.dto.LeaveApplyDto;
import com.hajimi.backend.mapper.LeaveMapper;
import com.hajimi.backend.service.LeaveService;
import com.hajimi.backend.vo.LeaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: jone
 * @Date: 2026/8/19 - 08 - 19
 * @Description: com.hajimi.backend.service.impl
 * @version: 1.0
 * 请假业务实现类
 */
@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveMapper leaveMapper;

    @Override
    public List<LeaveVo> listByMemberId(Integer id) {
        return null;
    }

    @Override
    public void apply(LeaveApplyDto dto) {
    }
}
