package com.hajimi.backend.utils;
import com.hajimi.backend.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public static class BizException extends RuntimeException {
        public BizException(String msg) {
            super(msg);
        }
    }
    //捕获我们自己抛出的业务异常
    @ExceptionHandler(BizException.class)
    public Result<String> handleBizException(BizException e){
        return Result.error(e.getMessage());
    }
}


