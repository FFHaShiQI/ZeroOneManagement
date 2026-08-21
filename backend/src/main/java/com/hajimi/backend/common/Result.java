package com.hajimi.backend.common;

<<<<<<< HEAD
import lombok.Data;
/**
 *
 * @param
 * @return
 */
@Data
public class Result {

    private Integer code; //编码：1成功，0为失败
    private String msg; //错误信息
    private Object data; //数据

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
=======
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
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
>>>>>>> feature/senior/leave-audit
    }
}
