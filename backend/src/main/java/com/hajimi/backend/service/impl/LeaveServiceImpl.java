package com.hajimi.backend.service.impl;

import com.hajimi.backend.dto.LeaveApplyDto;
import com.hajimi.backend.entity.LeaveApply;
import com.hajimi.backend.mapper.LeaveMapper;
import com.hajimi.backend.service.LeaveService;
import com.hajimi.backend.vo.LeaveVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 与或非
 * @Date: 2026/8/20 - 08 - 20
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
        List<LeaveApply> list = leaveMapper.listByMemberId(id);
        List<LeaveVo> leaveList = new ArrayList<>();
        for (LeaveApply leaveApply : list) {
            LeaveVo lv = new LeaveVo();
            BeanUtils.copyProperties(leaveApply, lv);
            leaveList.add(lv);
        }
        return leaveList;
    }

    @Override
    public void apply(LeaveApplyDto dto) {
        LeaveApply leaveApply = new LeaveApply();
        BeanUtils.copyProperties(dto, leaveApply);
        leaveApply.setStatus(0);
        leaveApply.setCreateTime(LocalDateTime.now());
        leaveMapper.insert(leaveApply);
    }
}
