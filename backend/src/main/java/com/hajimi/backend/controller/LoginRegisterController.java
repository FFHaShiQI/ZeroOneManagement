package com.hajimi.backend.controller;

import com.hajimi.backend.dto.LoginDto;
import com.hajimi.backend.dto.RegisterDto;
import com.hajimi.backend.vo.LoginVo;
import com.hajimi.backend.common.Result;
import com.hajimi.backend.service.LoginService;
import com.hajimi.backend.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Slf4j
public class LoginRegisterController {
    /**
     * W
     * 登录功能
     */

    @Autowired
    private LoginService loginService;
    @Autowired
    private RegisterService registerService;


    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDTO){
        LoginVo loginVO = loginService.login(loginDTO);
        if(loginVO == null){
            return Result.error("账号或密码错误");
        }
        return Result.success(loginVO);
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterDto registerDTO) {
        registerService.register(registerDTO);
        return Result.success();
    }
}
