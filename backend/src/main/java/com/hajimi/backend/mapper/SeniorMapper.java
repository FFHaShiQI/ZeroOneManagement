package com.hajimi.backend.mapper;

import com.hajimi.backend.vo.SeniorVo;
import com.hajimi.backend.dto.SeniorUpdateDto;
import com.hajimi.backend.entity.Senior;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Auther: Cat
 * @Date: 2026/8/19 - 08 - 19
 * @Description: com.hajimi.backend.mapper
 * @version: 1.0
 * 学长表数据访问接口
 */
@Mapper
public interface SeniorMapper {

    /**
     * @Auther: Cat
     * 根据学长编号查询学长
     * @param id 学长编号
     * @return 学长实体
     */
    @Select("select id, name, type, username, password, number, image from senior where id = #{id}")
    SeniorVo selectById(Integer id);

    /**
     * @param dto 学长实体（id 必填）
     * @Auther: Cat
     * 修改学长信息
     */
    void updateById(SeniorUpdateDto dto);

    /**
     * 登录:按用户名查询学长(密码为 BCrypt 密文,由服务层校验)
     * @param username 用户名
     * @return 学长实体
     */
    Senior getByUsername(String username);

    /**
     * 统计同名用户名数量(注册查重)
     * @param username 用户名
     * @return 数量
     */
    @Select("select count(*) from senior where username = #{username}")
    int countByUsername(String username);

    /**
     * 新增学长(注册)
     * @param senior 学长实体
     */
    @Insert("insert into senior (username, password) values (#{username}, #{password})")
    void saveInSenior(Senior senior);
}
