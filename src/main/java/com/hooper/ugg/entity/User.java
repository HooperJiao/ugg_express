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
 * @since 2025-10-17
 */
@Data
@TableName("ugg_user")
@Schema(name = "User", description = "")
public class User extends UggBase {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.ASSIGN_UUID)
    private String userId;

    @Schema(description = "1. 员工，2.普通用户")
    private Integer roleNo;

    @Schema(description = "存储UGG系统相关的唯一码")
    private String uggCode;

    @Schema(description = "用户账号，不可更改，且唯一")
    private String accountNumber;

    @Schema(description = "用户密码")
    private String userPassword;

    private String userName;

    @Schema(description = "真实姓名，修改需审核")
    private String realName;

    private String userPhone;

    private String userEmail;

    @Schema(description = "0.注销 1.正常启用，2.冻结")
    private Integer userStatus;

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