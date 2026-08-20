package com.hajimi.backend.controller;


import com.hajimi.backend.common.Result;
import com.hajimi.backend.dto.GroupQueryDto;
import com.hajimi.backend.entity.Group;
import com.hajimi.backend.service.SeniorGroupService;
import com.hajimi.backend.vo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/senior/groups")
@Slf4j


/**
 * 蓝宝827
 */
public class SeniorGroupController {

    @Autowired
    private SeniorGroupService seniorGroupService;

    /**
     * 条件分页查询
     * @return
     */
    @GetMapping
    public  Result pageQuery(GroupQueryDto groupQueryDto){
        log.info("{}",groupQueryDto);
        PageResult<Group> groupPageResult = seniorGroupService.pageQuery(groupQueryDto);
        return Result.success(groupPageResult);
    }


    /**
     * 添加小组
     * @return
     */
    @PostMapping
    public  Result addGroup(@RequestBody Group group){
        log.info("新增小组:{}",group);
        seniorGroupService.addGroup(group);
        return Result.success();
    }

    /**
     * 修改小组
     * @return
     */
    @PutMapping
    public  Result updateGroup(@RequestBody Group group){
        log.info("修改小组{}",group);
        seniorGroupService.updateGroup(group);
        return Result.success();
    }

    /**
     * 删除小组
     * @return
     */
    @DeleteMapping
    public  Result deleteGroup(@RequestParam List<Integer> groupIds){
        log.info("根据id删除小组:{}",groupIds);
        seniorGroupService.deleteById(groupIds);
        return  Result.success();
    }
}
