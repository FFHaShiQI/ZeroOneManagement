package com.hajimi.backend.entity;

import lombok.Data;

@Data
public class Group {
    private Integer id;
    private String name;
    private Integer language;
    private String clockLink;
    private Integer groupSize;
    private Integer seniorId;
}
