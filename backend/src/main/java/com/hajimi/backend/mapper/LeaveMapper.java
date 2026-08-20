package com.hajimi.backend.mapper;

import com.hajimi.backend.entity.LeaveApply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Auther: jone
 * @Date: 2026/8/19 - 08 - 19
 * @Description: com.hajimi.backend.mapper
 * @version: 1.0
 * 请假申请表数据访问接口
 */
@Mapper
public interface LeaveMapper {

    /**
     * 根据编号查询请假记录
     * @param id 请假记录编号
     * @return 请假申请实体
     */
    //LeaveApply getById(Integer id);

    /**
     * 查询某学员的全部请假记录
     * @param memberId 学员id
     * @return 请假记录列表
     */
    //List<LeaveApply> listByMemberId(Integer memberId);

    /**
     * 新增请假申请（初始状态为审核中）
     * @param leaveApply 请假申请实体
     * @return 影响行数
     */
    //int insert(LeaveApply leaveApply);
}
