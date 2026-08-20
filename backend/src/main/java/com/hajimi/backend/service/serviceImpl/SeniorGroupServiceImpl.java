package com.hajimi.backend.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hajimi.backend.dto.GroupQueryDto;
import com.hajimi.backend.entity.Group;
import com.hajimi.backend.mapper.SeniorGroupMapper;
import com.hajimi.backend.service.SeniorGroupService;
import com.hajimi.backend.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SeniorGroupServiceImpl implements SeniorGroupService {


    @Autowired
    private SeniorGroupMapper seniorGroupMapper;

    @Override
    public PageResult<Group> pageQuery(GroupQueryDto groupQueryDto) {
        PageHelper.startPage(groupQueryDto.getPage(),groupQueryDto.getPageSize());//设分页参数
        List<Group> groupList=seniorGroupMapper.select(groupQueryDto);//获取数据
        Page<Group>p=(Page<Group>)groupList;//获取total
        return  new PageResult<>(p.getTotal(),p.getResult());
    }

    @Override
    public void addGroup(Group group) {
        seniorGroupMapper.insert(group);
    }

    @Override
    public void updateGroup(Group group) {
        seniorGroupMapper.update(group);
    }

    @Override
    public void deleteById(List<Integer> groupIds) {
        seniorGroupMapper.deleteById(groupIds);
    }
}
