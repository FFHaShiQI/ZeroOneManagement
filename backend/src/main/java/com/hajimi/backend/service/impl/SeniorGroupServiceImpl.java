package com.hajimi.backend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hajimi.backend.dto.GroupQueryDto;
import com.hajimi.backend.entity.Group;
import com.hajimi.backend.exception.DatabaseDeletionException;
import com.hajimi.backend.mapper.SeniorGroupMapper;
import com.hajimi.backend.service.SeniorGroupService;
import com.hajimi.backend.vo.GroupIdName;
import com.hajimi.backend.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 小组服务实现类
 * 提供对小组的增删改查操作
 * @author Hajimi 蓝宝827
 */
@Service
public class SeniorGroupServiceImpl implements SeniorGroupService {
    @Autowired
    private SeniorGroupMapper seniorGroupMapper;

    /**
     * 条件分页查询
     * @param groupQueryDto 查询参数
     * @return 分页查询结果对象
     */
    @Override
    public PageResult<Group> pageQuery(GroupQueryDto groupQueryDto) {
        PageHelper.startPage(groupQueryDto.getPage(),groupQueryDto.getPageSize());//设分页参数
        List<Group> groupList=seniorGroupMapper.select(groupQueryDto);//查询数据
        Page<Group>p=(Page<Group>)groupList;//获取total和result
        return  new PageResult<>(p.getTotal(),p.getResult());
    }

    /**
     * 添加小组
     * @param group 小组信息
     */
    @Override
    public void addGroup(Group group) {
        seniorGroupMapper.insert(group);
    }

    /**
     * 修改小组
     * @param group 小组信息
     */
    @Override
    public void updateGroup(Group group) {
        seniorGroupMapper.update(group);
    }

    /**
     * 删除小组
     * @param groupIds 小组id列表
     */
    @Override
    public void deleteById(List<Integer> groupIds) {
        //判断是否有成员
        Integer memberCount=seniorGroupMapper.selectMemberById(groupIds);
        if(memberCount>0){
            throw new DatabaseDeletionException();//抛出异常给全局异常处理类
        }else {
            seniorGroupMapper.deleteById(groupIds);//删除小组
        }
    }

    @Override
    public List<GroupIdName> getAll() {

        return seniorGroupMapper.selectAll();
    }
}
