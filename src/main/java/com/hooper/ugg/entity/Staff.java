package com.hooper.ugg.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
@TableName("ugg_staff")
@Schema(name = "Staff", description = "")
public class Staff extends UggBase {

    private static final long serialVersionUID = 1L;

    @TableId(value = "staff_id", type = IdType.ASSIGN_UUID)
    private String staffId;

    private String userId;

    @Schema(description = "0.注销 1.正常启用")
    private Integer staffStatus;

    private String realName;

    private String preferName;

    private String referenceName;

    private String phone;

    private String email;

    private String address;

    private String tfnTax;

    private String lightSpeedAccount;

    private String lightSpeedPassword;

    private String warrantyAccount;

    private String warrantyPass;

    private String storeNumber1;

    private String storeName1;

    private String storeNumber2;

    private String storeName2;

    private String storeNumber3;

    private String storeName3;

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