package com.hajimi.backend.mapper;

import com.hajimi.backend.dto.LeaveApplyDto;
import com.hajimi.backend.dto.LeaveAuditDto;
import com.hajimi.backend.vo.LeaveApplyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LeavesMapper {

    /**
     * W
     * dto
     * 访问数据库的接口
     */
    List<LeaveApplyVo> fuzzySelect(@Param("dto") LeaveApplyDto dto);

    /**
     * 请假审核 更新leave_apply主表
     */
    void audit(LeaveAuditDto leaveAuditDto);

    /**
     * 删除请假记录
     */
    void deleteLeaveById(@Param("id") Integer id);
    /**
     * 检查某个成员是否有审核中的请假记录
     * @param memberId 成员ID
     * @return 审核中的请假记录数量
     */
    @Select("SELECT COUNT(*) FROM leaveapply WHERE member_id = #{memberId}")
    Long countPendingByMemberId(@Param("memberId") Integer memberId);
}
