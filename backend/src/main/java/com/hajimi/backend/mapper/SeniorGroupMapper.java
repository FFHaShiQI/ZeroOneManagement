package com.hajimi.backend.mapper;

import com.hajimi.backend.dto.GroupQueryDto;
import com.hajimi.backend.entity.Group;
import com.hajimi.backend.vo.GroupIdName;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 学长小组数据库操作接口
 * 提供对学长小组的增删改查操作
 * @author Hajimi 蓝宝827
 */
@Mapper
public interface SeniorGroupMapper {

    void insert(Group group);

    void update(Group group);

    void deleteById(List<Integer> groupIds);

    List<Group> select(GroupQueryDto groupQueryDto);

    Integer selectMemberById(List<Integer> groupIds);

    @Select("select id,name from `group`")
    List<GroupIdName> selectAll();
}
