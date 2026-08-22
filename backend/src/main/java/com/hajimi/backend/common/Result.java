package com.hajimi.backend.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    /**
     * W
     * 返回结果
     */
    private Integer code;
    private String msg;
    private T data; // 修改：不再是List<T>

    // 成功，不带数据
    public static <T> Result<T> success() {
        return new Result<>(1,"success",null);
    }
    // 成功，携带任意数据（可以是PageResult，可以是List，可以是对象）
    public static <T> Result<T> success(T data) {
        return new Result<>(1,"success",data);
    }
    // 失败
    public static <T> Result<T> error(String msg) {
        return new Result<>(0, msg,null);
    }
}
