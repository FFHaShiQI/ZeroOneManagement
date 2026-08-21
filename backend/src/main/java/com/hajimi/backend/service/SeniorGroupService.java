package com.hajimi.backend.service;

import com.hajimi.backend.dto.GroupQueryDto;
import com.hajimi.backend.entity.Group;
import com.hajimi.backend.vo.PageResult;

import java.util.List;

/**
 * 学长小组服务接口
 * 提供对学长小组的增删改查操作
 * @author Hajimi 蓝宝827
 */
public interface SeniorGroupService {
    /**
     * 条件分页查询
     * @param groupQueryDto 查询参数
     * @return 分页查询结果
     */
    PageResult<Group> pageQuery(GroupQueryDto groupQueryDto);

    /**
     * 添加小组
     * @param group 小组信息
     */
    void addGroup(Group group);

    /**
     * 更新小组
     * @param group 小组信息
     */
    void updateGroup(Group group);

    /**
     * 删除小组
     * @param groupIds 小组id列表
     */
    void deleteById(List<Integer> groupIds);
}
