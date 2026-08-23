package com.hajimi.backend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hajimi.backend.dto.LeaveApplyDto;
import com.hajimi.backend.entity.PageResult;
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
        // 默认值：第1页，每页10条
        Integer pageNum = leaveApplyDto.getPageNum();
        Integer pageSize = leaveApplyDto.getPageSize();
        if(pageNum == null) pageNum = 1;
        if(pageSize == null) pageSize = 10;

        //设置分页参数（pageHelper）
        PageHelper.startPage(pageNum, pageSize);

        //执行查询
        List<LeaveApplyVo> leaveList = leavesMapper.fuzzySelect(leaveApplyDto);

        //解析查询结果，并封装
        Page<LeaveApplyVo> page = (Page<LeaveApplyVo>) leaveList;
        return new PageResult<>(page.getTotal(),page.getResult());

    }
}
