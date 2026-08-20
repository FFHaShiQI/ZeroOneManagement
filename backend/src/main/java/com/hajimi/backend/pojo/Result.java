package com.hajimi.backend.pojo;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;


/**
 * 规范后端返回给前端的数据格式
 * 例如：
 *   {
 *     "code": 1,
 *     "msg": "操作成功",
 *     "data": ...
 *   }
 */
@Data
@JsonPropertyOrder({"code", "msg", "data"})     // 指定返回结果时的JSON字段顺序，不加容易乱序
public class Result {

    private Integer code; // 编码：1成功，0为失败
    private String msg; // 正确/错误信息
    private Object data; // 数据

    public static Result success() {
        Result result = new Result();
        result.code = 1;
        result.msg = "success";
        return result;
    }

    public static Result success(Object object) {
        Result result = new Result();
        result.data = object;
        result.code = 1;
        result.msg = "success";
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }

}
