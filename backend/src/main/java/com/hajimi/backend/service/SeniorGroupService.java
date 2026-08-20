package com.hajimi.backend.service;

import com.hajimi.backend.dto.GroupQueryDto;
import com.hajimi.backend.entity.Group;
import com.hajimi.backend.vo.PageResult;

import java.util.List;

public interface SeniorGroupService {


    PageResult<Group> pageQuery(GroupQueryDto groupQueryDto);

    void addGroup(Group group);

    void updateGroup(Group group);

    void deleteById(List<Integer> groupIds);
}
