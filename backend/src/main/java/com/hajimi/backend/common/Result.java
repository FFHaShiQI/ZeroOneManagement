package com.hajimi.backend.common;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import lombok.Data;
/**
 *
 * @param
 * @return
 */
@Data
public class Result {

=======
=======
>>>>>>> feature/senior/profile
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
<<<<<<< HEAD
 * @Auther: 与或非
 * @Date: 2026/8/20 - 08 - 20
=======
 * @Auther: jone
 * @Date: 2026/8/19 - 08 - 19 - 16:26
>>>>>>> feature/senior/profile
 * @Description: com.hajimi.backend.common
 * @version: 1.0
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
<<<<<<< HEAD
public class Result {
>>>>>>> feature/member
    private Integer code; //编码：1成功，0为失败
    private String msg; //错误信息
    private Object data; //数据
=======
public class Result<T> {
    private Integer code; //编码：1成功，0为失败
    private String msg; //错误信息
    private T data; //数据
>>>>>>> feature/senior/profile

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
<<<<<<< HEAD
<<<<<<< HEAD
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
=======
>>>>>>> feature/member
=======
>>>>>>> feature/senior/profile
    }
}
