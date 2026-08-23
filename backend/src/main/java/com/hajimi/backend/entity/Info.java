package com.hajimi.backend.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Info {
    private Integer id;
    private String title;
    private String content;
    private Integer priority;
    private LocalDateTime publishTime;
    private Integer seniorId;
}
