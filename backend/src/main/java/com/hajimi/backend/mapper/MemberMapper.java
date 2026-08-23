package com.hajimi.backend.mapper;

import com.hajimi.backend.dto.MemberUpdateDto;
import com.hajimi.backend.entity.Member;
import com.hajimi.backend.vo.MemberVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Auther: 与或非
 * @Date: 2026/8/20 - 08 - 20
 * @Description: com.hajimi.backend.mapper
 * @version: 1.0
 * 组员表数据访问接口
 */
@Mapper
public interface MemberMapper {

    /**
     * 根据组员编号查询单个组员
     * @param id 组员编号
     * @return 组员实体
     */
    MemberVo getById(Integer id);

    /**
     * 修改组员信息
     * @param dto 组员实体（id 必填）
     * @return 影响行数
     */
    void update(MemberUpdateDto dto);

    /**
     * 根据小组id查询组员信息
     * @param groupId 组员实体（id 必填）
     * @return 影响行数
     */
    @Select("select id,name,gender,age,image,number,pos " +
            "from member where group_id = #{groupId}")
    List<MemberVo> getByGroupId(Integer groupId);

    /**
     * 登录:按用户名查询组员(密码为 BCrypt 密文,由服务层校验)
     * @param username 用户名
     * @return 组员实体
     */
    Member getByUsername(String username);

    /**
     * 统计同名用户名数量(注册查重)
     * @param username 用户名
     * @return 数量
     */
    @Select("select count(*) from member where username = #{username}")
    int countByUsername(String username);

    /**
     * 新增组员(注册)
     * @param member 组员实体
     */
    @Insert("insert into member (username, password, group_id) values (#{username}, #{password}, #{groupId})")
    void saveInMember(Member member);
}
