package com.hajimi.backend.mapper;

import com.hajimi.backend.pojo.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GroupMapper {

    // 根据id查询小组信息
    @Select("select id,name,language,clock_link,group_size,senior_id " +
            "from `group` where id = #{groupId}")
    Group selectByID(Integer groupId);
}
