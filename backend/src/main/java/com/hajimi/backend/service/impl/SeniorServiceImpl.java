package com.hajimi.backend.service.impl;

import com.hajimi.backend.dto.SeniorUpdateDto;
import com.hajimi.backend.entity.Senior;
import com.hajimi.backend.mapper.SeniorMapper;
import com.hajimi.backend.service.SeniorService;
import com.hajimi.backend.vo.SeniorIdName;
import com.hajimi.backend.vo.SeniorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Cat
 * @Date: 2026/8/19 - 08 - 19
 * @Description: com.hajimi.backend.service.impl
 * @version: 1.0
 * 学长业务实现类
 */
@Service
public class SeniorServiceImpl implements SeniorService {

    @Autowired
    private SeniorMapper seniorMapper;

    @Override
    public SeniorVo getById(Integer id) {
        return seniorMapper.selectById(id);
    }

    @Override
    public void updateById(SeniorUpdateDto dto) {
        seniorMapper.updateById(dto);
    }

    @Override
    public List<SeniorIdName> getAll() {
        return seniorMapper.selectAll();
    }
}
