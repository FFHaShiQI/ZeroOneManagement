package com.hajimi.backend.service;

import com.hajimi.backend.DTO.LoginDTO;
import com.hajimi.backend.VO.LoginVO;

public interface LoginService {
    /**
     *W
     * 登录功能实现类的接口
     *loginDTO
     */
    LoginVO login(LoginDTO loginDTO);
}
