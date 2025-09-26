package com.hooper.ugg.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Hooper
 * @since 2025-09-25
 */
@Data
@TableName("ugg_user")
@Schema(name = "User", description = "")
public class User extends UggBase {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.ASSIGN_UUID)
    private String userId;

    private String userCode;

    private String userName;

    private String realName;

    private String referenceName;

    private String userPhone;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}