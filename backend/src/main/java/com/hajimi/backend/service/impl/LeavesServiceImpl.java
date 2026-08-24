package com.hajimi.backend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hajimi.backend.dto.LeaveApplyDto;
import com.hajimi.backend.vo.PageResult;
import com.hajimi.backend.mapper.LeavesMapper;
import com.hajimi.backend.service.LeavesService;
import com.hajimi.backend.vo.LeaveApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeavesServiceImpl implements LeavesService {

    @Autowired
    private LeavesMapper leavesMapper;

    /**
     * W
     * 基于pageHelper实现分页查询
     * leaveApplyDto
     */
    @Override
    public PageResult<LeaveApplyVo> fuzzyQuery(LeaveApplyDto leaveApplyDto) {
        //设置分页参数（pageHelper）,未传时分页参数使用默认值
        if (leaveApplyDto.getPage() == null) {
            leaveApplyDto.setPage(1);
        }
        if (leaveApplyDto.getPageSize() == null) {
            leaveApplyDto.setPageSize(10);
        }
        PageHelper.startPage(leaveApplyDto.getPage(), leaveApplyDto.getPageSize());

        //执行查询
        List<LeaveApplyVo> leaveList = leavesMapper.fuzzySelect(leaveApplyDto);

        //解析查询结果，并封装
        Page<LeaveApplyVo> page = (Page<LeaveApplyVo>) leaveList;
        return new PageResult<>(page.getTotal(),page.getResult());

    }
}
