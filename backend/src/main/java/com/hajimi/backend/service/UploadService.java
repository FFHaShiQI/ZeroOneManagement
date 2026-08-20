package com.hajimi.backend.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther: jone
 * @Date: 2026/8/19 - 08 - 19
 * @Description: com.hajimi.backend.service
 * @version: 1.0
 * 文件上传业务接口
 */
public interface UploadService {

    /**
     * 上传文件并返回访问 URL
     * @param file 上传的文件
     * @param bizType 业务类型：avatar-头像 / attachment-附件 / idcard-证件照
     * @return 文件访问 URL
     */
    String upload(MultipartFile file, String bizType);
}
