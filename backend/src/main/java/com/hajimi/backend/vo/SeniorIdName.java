package com.hajimi.backend.vo;

import lombok.Data;

/**
 * 学长 id + 姓名(下拉选项场景使用)
 */
@Data
public class SeniorIdName {
    private Integer id;
    private String name;
}
