package com.hajimi.backend.service;

import com.hajimi.backend.dto.LoginDto;
import com.hajimi.backend.vo.LoginVo;

public interface LoginService {
    /**
     *W
     * 登录功能实现类的接口
     *loginDTO
     */
    LoginVo login(LoginDto loginDTO);
}
