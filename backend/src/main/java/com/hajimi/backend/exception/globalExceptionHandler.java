package com.hajimi.backend.exception;


import com.hajimi.backend.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 * 提供对全局异常的处理
 * @author Hajimi 蓝宝827
 */
@Slf4j
@RestControllerAdvice
public class globalExceptionHandler {
    @ExceptionHandler(DatabaseDeletionException.class)
    public Result databaseDeletionException(DatabaseDeletionException e) {
        log.error("数据库删除异常", e);
        return Result.error("数据库删除异常");
    }
}
