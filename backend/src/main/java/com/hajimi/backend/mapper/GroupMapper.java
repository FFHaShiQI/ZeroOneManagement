package com.hajimi.backend.mapper;

import com.hajimi.backend.entity.Group;
import com.hajimi.backend.vo.GroupVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Auther: 与或非
 * @Date: 2026/8/20 - 08 - 20
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
    GroupVo getById(Integer id);
}
