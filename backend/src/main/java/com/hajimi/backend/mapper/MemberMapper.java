package com.hajimi.backend.mapper;

import com.hajimi.backend.dto.MemberQueryDto;
import com.hajimi.backend.dto.MemberUpdateDto;
import com.hajimi.backend.entity.Member;
import com.hajimi.backend.vo.MemberVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 新增组员(学长添加,全字段,自增id回填)
     * @param member 组员实体
     * @return 影响行数
     */
    int insert(Member member);

    /**
     * 分页模糊查询组员(按昵称/小组名模糊,按性别/职位筛选)
     * @param query 查询条件
     * @param offset 偏移量
     * @return 组员列表
     */
    List<MemberVo> pageQuery(@Param("query") MemberQueryDto query, @Param("offset") int offset);

    /**
     * 统计符合条件的组员总数
     * @param query 查询条件
     * @return 总记录数
     */
    Long count(@Param("query") MemberQueryDto query);

    /**
     * 批量删除组员
     * @param ids 组员编号列表
     * @return 影响行数
     */
    int deleteByIds(@Param("list") List<Integer> ids);
}
