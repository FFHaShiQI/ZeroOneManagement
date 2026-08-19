package com.hajimi.backend.controller;


import com.hajimi.backend.common.Result;
import com.hajimi.backend.service.SeniorGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/senior/groups")
@Slf4j
public class SeniorGroupController {

    @Autowired
    private SeniorGroupService seniorGroupService;

    @GetMapping
    public  Result pageQUERY(){
        return Result.success();
    }

    @PostMapping
    public  Result addGroup(){

        return Result.success();
    }

    @PutMapping
    public  Result updateGroup(){
        return Result.success();
    }

    @DeleteMapping
    public  Result deleteGroup(){
        return  Result.success();
    }


}
