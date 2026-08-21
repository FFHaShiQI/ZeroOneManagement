package com.hajimi.backend.controller;

import com.hajimi.backend.common.AliyunOSSOperator;
import com.hajimi.backend.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther: 与或非
 * @Date: 2026/8/21 - 08 - 21
 * @Description: com.hajimi.backend.controller
 * @version: 1.0
 */

@Slf4j
@RestController
public class UploadController {

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        log.info("文件上传：{}", file.getOriginalFilename());
        String url = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
        return Result.success(url);
    }
}
