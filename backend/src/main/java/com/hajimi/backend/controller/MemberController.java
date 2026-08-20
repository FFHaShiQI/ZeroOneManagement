package com.hajimi.backend.controller;

import com.hajimi.backend.pojo.Group;
import com.hajimi.backend.pojo.Member;
import com.hajimi.backend.pojo.Result;
import com.hajimi.backend.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 查询组员个人信息
    @GetMapping
    public Result selectMember(Integer id) {
        log.info("根据id查询组员个人信息：{}", id);
        Member member = memberService.selectMember(id);
        return Result.success(member);
    }

    // 修改组员个人信息
    @PutMapping
    public Result updateMember(@RequestBody Member member){
        log.info("修改组员个人信息：{}", member);
        memberService.updateMember(member);
        return Result.success();
    }

    // 查询个人所在小组信息
    @GetMapping("/group")
    public Result selectGroup(Integer groupId){
        log.info("根据groupId查询小组信息：{}", groupId);
        Group group = memberService.selectGroup(groupId);
        return Result.success(group);
    }
}
