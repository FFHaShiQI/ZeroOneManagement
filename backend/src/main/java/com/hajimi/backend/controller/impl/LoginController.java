package com.hajimi.backend.controller.impl;

import com.hajimi.backend.DTO.LoginDTO;
import com.hajimi.backend.VO.LoginVO;
import com.hajimi.backend.common.Result;
import com.hajimi.backend.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Slf4j
public class LoginController {
    /**
     * W
     * 登录功能
     */

    @Autowired
    private LoginService loginService;


    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO){
        LoginVO loginVO = loginService.login(loginDTO);
        if(loginVO == null){
            return Result.error("账号或密码错误");
        }
        return Result.success(loginVO);
    }
}
