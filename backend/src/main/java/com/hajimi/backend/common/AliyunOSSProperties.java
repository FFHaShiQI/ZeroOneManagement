package com.hajimi.backend.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 将application.yml中的与阿里云配置相关的参数进行封装，便于后续的维护
 *
 * 主要是用来辅助AliyunOSSOperator工具类的
 **/

/**
 * @Auther: 与或非
 * @Date: 2026/8/21 - 08 - 21
 * @Description: com.hajimi.backend.common
 * @version: 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOSSProperties {
    private String endpoint;
    private String bucketName;
    private String region;
    private String keyId;
    private String keySecret;
}
