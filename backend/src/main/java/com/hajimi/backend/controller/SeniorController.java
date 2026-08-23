package com.hajimi.backend.controller;

import com.hajimi.backend.common.Result;
import com.hajimi.backend.dto.SeniorUpdateDto;
import com.hajimi.backend.service.SeniorService;
import com.hajimi.backend.vo.SeniorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: Cat
 * @Date: 2026/8/19 - 08 - 19
 * @Description: com.hajimi.backend.controller
 * @version: 1.0
 * 学长个人中心接口
 */
@RestController
@RequestMapping("/senior")
public class SeniorController {

    @Autowired
    private SeniorService seniorService;

    /**
     * @Auther: Cat
     * 获取学长个人信息
     * @param id 学长编号
     * @return 学长信息
     */
    @GetMapping
    public Result<SeniorVo> getSenior(@RequestParam Integer id) {
        return Result.success(seniorService.getById(id));
    }

    /**
     * @Auther: Cat
     * 修改学长个人信息
     * @param dto 修改学长信息请求体（id 必填）
     * @return 统一响应
     */
    @PutMapping
    public Result updateSenior(@RequestBody SeniorUpdateDto dto) {
        seniorService.updateById(dto);

        return Result.success();
    }

    /**
     * 查询全部学长(id + 姓名,供前端下拉选择)
     * @return 学长选项列表
     */
    @GetMapping("/All")
    public Result getAllSeniors() {
        return Result.success(seniorService.getAll());
    }
}
