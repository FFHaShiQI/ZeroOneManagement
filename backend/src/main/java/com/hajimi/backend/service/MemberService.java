package com.hajimi.backend.service;

import com.hajimi.backend.dto.MemberUpdateDto;
import com.hajimi.backend.vo.GroupVo;
import com.hajimi.backend.vo.MemberVo;

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
     * 查询所属小组信息
     * @param groupId 修改组员请求体（id 必填）
     */
    GroupVo getGroup(Integer groupId);
}
