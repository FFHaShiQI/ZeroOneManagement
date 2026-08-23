package com.hajimi.backend.mapper;

import com.hajimi.backend.entity.Senior;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SeniorMapper {
    /**
     * W
     * 学长的数据库访问接口
     * username
     * password
     */
    Senior getByUsername(String username);

    void saveInSenior(Senior senior);
}

