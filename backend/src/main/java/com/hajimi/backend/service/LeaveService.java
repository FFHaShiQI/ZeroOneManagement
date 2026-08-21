package com.hajimi.backend.service;

import com.hajimi.backend.dto.LeaveApplyDto;
import com.hajimi.backend.vo.LeaveVo;

import java.util.List;

/**
 * @Auther: 与或非
 * @Date: 2026/8/20 - 08 - 20
 * @Description: com.hajimi.backend.service
 * @version: 1.0
 * 请假业务接口：申请 / 列表查询 / 分页 / 审批 / 删除
 */
public interface LeaveService {

    /**
     * 查询某学员的全部请假记录
     * @param id 学员id
     * @return 请假记录列表
     */
    List<LeaveVo> listByMemberId(Integer id);

    /**
     * 组员申请请假
     * @param dto 请假申请请求体
     */
    void apply(LeaveApplyDto dto);
}
