package com.hajimi.backend.mapper;

import com.hajimi.backend.entity.Senior;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Auther: jone
 * @Date: 2026/8/19 - 08 - 19
 * @Description: com.hajimi.backend.mapper
 * @version: 1.0
 * 学长表数据访问接口
 */
@Mapper
public interface SeniorMapper {

    /**
     * 根据学长编号查询学长
     * @param id 学长编号
     * @return 学长实体
     */
    //Senior getById(Integer id);

    /**
     * 根据用户名查询学长（登录/注册查重用）
     * @param username 用户名
     * @return 学长实体
     */
    //Senior getByUsername(String username);

    /**
     * 新增学长
     * @param senior 学长实体
     * @return 影响行数
     */
    //int insert(Senior senior);

    /**
     * 修改学长信息
     * @param senior 学长实体（id 必填）
     * @return 影响行数
     */
    //int update(Senior senior);
}
