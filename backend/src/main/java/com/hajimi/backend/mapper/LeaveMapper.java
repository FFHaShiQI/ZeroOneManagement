package com.hajimi.backend.mapper;

import com.hajimi.backend.entity.LeaveApply;
import com.hajimi.backend.vo.LeaveVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Auther: 与或非
 * @Date: 2026/8/21 - 08 - 21
 * @Description: com.hajimi.backend.mapper
 * @version: 1.0
 * 请假申请表数据访问接口
 */
@Mapper
public interface LeaveMapper {
    /**
     * 查询某学员的全部请假记录
     * @param memberId 学员id
     * @return 请假记录列表
     */
    List<LeaveVo> listByMemberId(Integer memberId);

    /**
     * 新增请假申请（初始状态为审核中）
     * @param leaveApply 请假申请实体
     * @return 影响行数
     */
    @Insert("insert into leaveapply(member_id,reason,type,image,start,end,status,create_time) " +
            "values (#{memberId},#{reason},#{type},#{image},#{start},#{end},#{status},#{createTime})")
    void insert(LeaveApply leaveApply);
}
