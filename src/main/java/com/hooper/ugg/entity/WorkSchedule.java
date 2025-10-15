package com.hooper.ugg.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Hooper
 * @since 2025-10-15
 */
@Data
@TableName("ugg_work_schedule")
@Schema(name = "WorkSchedule", description = "")
public class WorkSchedule extends UggBase {

    private static final long serialVersionUID = 1L;

    @TableId(value = "work_schedule_id", type = IdType.ASSIGN_UUID)
    private String workScheduleId;

    private String staffId;

    private BigDecimal hour;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String weekday;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime updateTime;

}