package com.hajimi.backend.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String msg;
    private Object data;

    public static Result success(){
        Result result =new Result();
        result.code=1;
        result.msg="success";
        return result;
    }
    public static Result success(Object object){
        Result result =new Result();
        result.code=1;
        result.msg="success";
        result.data=object;
        return result;
    }
    public static Result error(String msg){
        Result result =new Result();
        result.code=0;
        result.msg=msg;
        return result;
    }


}
