package com.hajimi.backend.dto;

import lombok.Data;
<<<<<<< HEAD

import java.time.LocalDate;

@Data
public class LeaveApplyDto {
    /**
     * 结束时间
     */
    private LocalDate end;
    /**
     * 按学员昵称模糊搜索
     */
    private String name;
    /**
     * 开始时间
     */
    private LocalDate start;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 请假类型
     */
    private Integer type;
    /**
     * 页码
     */
    private Integer pageNum;
    /**
     * 页大小
     */
    private Integer pageSize;
//    public LocalDate getEnd() { return end; }
//    public void setEnd(LocalDate value) { this.end = value; }
//
//    public String getName() { return name; }
//    public void setName(String value) { this.name = value; }
//
//    public LocalDate getStart() { return start; }
//    public void setStart(LocalDate value) { this.start = value; }
//
//    public Long getStatus() { return status; }
//    public void setStatus(Long value) { this.status = value; }
//
//    public Long getType() { return type; }
//    public void setType(Long value) { this.type = value; }
}
=======
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @Auther: 与或非
 * 2026/8/20 - 08 - 20
 * @Description: com.hajimi.backend.dto
 * @version: 1.0
 * 组员申请请假请求体
 */
@Data
public class LeaveApplyDto {
    private Integer memberId;   //请假的学员id
    private Integer type;       //请假类型：0病假1事假2其它
    private String reason;      //请假理由
    private String image;       //佐证图片（如病例、伤口图片等）
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;       //请假开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;         //请假结束时间
}
>>>>>>> feature/member
