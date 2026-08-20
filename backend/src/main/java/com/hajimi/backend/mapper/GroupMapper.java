package com.hajimi.backend.mapper;

import com.hajimi.backend.entity.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Auther: jone
 * @Date: 2026/8/19 - 08 - 19
 * @Description: com.hajimi.backend.mapper
 * @version: 1.0
 * 小组表数据访问接口
 */
@Mapper
public interface GroupMapper {

    /**
     * 根据小组编号查询小组
     * @param id 小组编号
     * @return 小组实体
     */
    @Select("select id,name,language,clock_link,group_size,senior_id " +
            "from `group` where id = #{id}")
    Group getById(Integer id);
}
