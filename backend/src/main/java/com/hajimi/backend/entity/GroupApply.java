package com.hajimi.backend.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GroupApply {
    private Integer id;
    private Integer memberId;
    private Integer groupIdOut;
    private Integer groupIdIn;
    private Integer status;
    private LocalDateTime applyTime;
}
