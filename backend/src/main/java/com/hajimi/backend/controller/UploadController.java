package com.hajimi.backend.controller;

import com.hajimi.backend.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther: jone
 * @Date: 2026/8/19 - 08 - 19 - 17:13
 * @Description: com.hajimi.backend.controller
 * @version: 1.0
 */
@RestController
@RequestMapping
@Slf4j
public class UploadController {
    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file){
        //  后端生成了头像url,返回这个url的值
        return Result.success("url(要修改)");
    }
}
