package com.hajimi.backend.service;

import com.hajimi.backend.dto.SeniorUpdateDto;
import com.hajimi.backend.Vo.SeniorVo;

/**
 * @Auther: Cat
 * @Date: 2026/8/19 - 08 - 19
 * @Description: com.hajimi.backend.service
 * @version: 1.0
 * 学长业务接口：查询 / 修改个人信息
 */
public interface SeniorService {

    /**
     * 根据学长编号查询个人信息
     * @param id 学长编号
     * @return 学长信息
     */
    SeniorVo getById(Integer id);

    /**
     * 修改学长个人信息
     * @param dto 修改学长信息请求体（id 必填）
     */
    void updateById(SeniorUpdateDto dto);
}
