package com.hajimi.backend.controller;

import com.hajimi.backend.common.Result;
import com.hajimi.backend.dto.MemberAddDto;
import com.hajimi.backend.dto.MemberQueryDto;
import com.hajimi.backend.dto.MemberUpdateDto;
import com.hajimi.backend.service.MemberService;
import com.hajimi.backend.vo.MemberVo;
import com.hajimi.backend.vo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: 天坑
 * @Date: 2026/21
 * @Description: com.hajimi.backend.controller
 * @version: 1.0
 * 学长端接口：组员管理
 */
@RestController
@RequestMapping("/senior/members")
@Slf4j
public class SeniorMemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 分页模糊查询组员
     *
     * @param query 查询条件（昵称/性别/职位/小组名/分页）
     * @return 分页结果
     */
    @GetMapping
    public Result<PageResult<MemberVo>> pageQuery(MemberQueryDto query) {
        log.info("分页查询组员, query: {}", query);
        PageResult<MemberVo> pageResult = memberService.pageQuery(query);
        return Result.success(pageResult);
    }

    /**
     * 添加组员
     *
     * @param dto 新增组员请求体
     * @return 统一响应
     */
    @PostMapping
    public Result addMember(@RequestBody MemberAddDto dto) {
        log.info("添加组员, dto: {}", dto);
        try {
            memberService.add(dto);
            return Result.success();
        } catch (RuntimeException e) {
            // 处理业务异常（如用户名已存在、邮箱已注册等）
            log.warn("添加组员失败: {}", e.getMessage());
            return Result.error(e.getMessage());
        } catch (Exception e) {
            // 处理其他未知异常
            log.error("添加组员失败", e);
            return Result.error("添加组员失败，请稍后重试");
        }
    }

    /**
     * 修改组员信息
     *
     * @param dto 修改组员请求体（id 必填）
     * @return 统一响应
     */
    @PutMapping
    public Result updateMember(@RequestBody MemberUpdateDto dto) {
        log.info("修改组员, dto: {}", dto);
        memberService.update(dto);
        return Result.success();
    }

    /**
     * 批量删除组员
     *
     * @param ids 组员编号（逗号分隔，如 "1,2,3"）
     * @return 统一响应
     */
    @DeleteMapping
    public Result deleteMembers(@RequestParam String ids) {
        log.info("批量删除组员, ids: {}", ids);
        memberService.deleteByIds(ids);
        return Result.success();
    }
}
