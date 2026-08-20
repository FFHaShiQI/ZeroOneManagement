package com.hajimi.backend.controller;

import com.hajimi.backend.common.Result;
import com.hajimi.backend.dto.LeaveApplyDto;
import com.hajimi.backend.dto.MemberUpdateDto;
import com.hajimi.backend.service.LeaveService;
import com.hajimi.backend.service.MemberService;
import com.hajimi.backend.vo.GroupVo;
import com.hajimi.backend.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: 与或非
 * @Date: 2026/8/20 - 08 - 20
 * @Description: com.hajimi.backend.controller
 * @version: 1.0
 * 组员端接口：个人信息 / 查询小组 / 请假
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private LeaveService leaveService;

    /**
     * 查询个人信息
     * @param id 组员编号
     * @return 组员信息
     */
    @GetMapping
    public Result getMember(@RequestParam Integer id) {
        MemberVo mv = memberService.getById(id);
        return Result.success(mv);
    }

    /**
     * 修改个人信息
     * @param dto 修改组员请求体（id 必填）
     * @return 统一响应
     */
    @PutMapping
    public Result updateMember(@RequestBody MemberUpdateDto dto) {
        memberService.update(dto);
        return Result.success();
    }

    /**
     * 查询所属小组信息
     * @param groupId 小组编号
     * @return 小组信息
     */
    @GetMapping("/group")
    public Result getGroup(@RequestParam Integer groupId) {
        GroupVo gv = memberService.getGroup(groupId);
        return Result.success(gv);
    }

    /**
     * 查询个人请假列表
     * @param id 组员编号
     * @return 请假记录列表
     */
    @GetMapping("/leaveApply")
    public Result getLeaveList(@RequestParam Integer id) {
        return null;
    }

    /**
     * 申请请假
     * @param dto 请假申请请求体
     * @return 统一响应
     */
    @PostMapping("/leaveApply")
    public Result applyLeave(@RequestBody LeaveApplyDto dto) {
        return null;
    }
}
