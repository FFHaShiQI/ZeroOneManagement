package com.hajimi.backend.service;

import com.hajimi.backend.dto.MemberAddDto;
import com.hajimi.backend.dto.MemberQueryDto;
import com.hajimi.backend.dto.MemberUpdateDto;
import com.hajimi.backend.vo.GroupVo;
import com.hajimi.backend.vo.MemberVo;
import com.hajimi.backend.vo.PageResult;

/**
 * @Auther: 与或非
 * @Date: 2026/8/20 - 08 - 20
 * @Description: com.hajimi.backend.service
 * @version: 1.0
 * 组员业务接口：查询 / 修改 / 分页 / 新增 / 批量删除
 */
public interface MemberService {

    /**
     * 根据组员编号查询个人信息
     * @param id 组员编号
     * @return 组员信息
     */
    MemberVo getById(Integer id);

    /**
     * 修改组员个人信息
     * @param dto 修改组员请求体（id 必填）
     */
    void update(MemberUpdateDto dto);

    /**
     * 分页模糊查询组员
     * @param query 查询条件（昵称/性别/职位/小组名/分页）
     * @return 分页结果
     */
    PageResult<MemberVo> pageQuery(MemberQueryDto query);

    /**
     * 学长添加组员
     * @param dto 新增组员请求体
     */
    void add(MemberAddDto dto);

    /**
     * 批量删除组员
     * @param ids 组员编号（逗号分隔，如 "1,2,3"）
     */
    void deleteByIds(String ids);

    /**
     * 查询所属小组信息
     * @param groupId 修改组员请求体（id 必填）
     */
    GroupVo getGroup(Integer groupId);

}
