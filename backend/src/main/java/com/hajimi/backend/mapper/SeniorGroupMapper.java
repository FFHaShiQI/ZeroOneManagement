package com.hajimi.backend.mapper;

import com.hajimi.backend.dto.GroupQueryDto;
import com.hajimi.backend.entity.Group;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper

public interface SeniorGroupMapper {

    void insert(Group group);

    void update(Group group);

    void deleteById(List<Integer> groupIds);

    List<Group> select(GroupQueryDto groupQueryDto);
}
