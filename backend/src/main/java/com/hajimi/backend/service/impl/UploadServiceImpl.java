package com.hajimi.backend.service.impl;

import com.hajimi.backend.service.UploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther: jone
 * @Date: 2026/8/19 - 08 - 19
 * @Description: com.hajimi.backend.service.impl
 * @version: 1.0
 * 文件上传业务实现类
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Override
    public String upload(MultipartFile file, String bizType) {
        return null;
    }
}
