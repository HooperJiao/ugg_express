package com.hooper.ugg.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
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
@TableName("ugg_payslip")
@Schema(name = "Payslip", description = "")
public class Payslip extends UggBase {

    private static final long serialVersionUID = 1L;

    @Schema(description = "薪资编号")
    @TableId(value = "payslip_id", type = IdType.ASSIGN_UUID)
    private String payslipId;

    @Schema(description = "工资单序号")
    private Integer payslipNumber;

    @Schema(description = "员工编号_外键")
    private String staffId;

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "存储路径_相对路径")
    private String filePath;

    @Schema(description = "真名")
    private String realName;

    @Schema(description = "薪资起始日期 ")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate payPeriodStart;

    @Schema(description = "薪资截止日期 ")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate payPeriodEnd;

    @Schema(description = "实际发薪日期 ")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate paymentDate;

    @Schema(description = "总计（本次）")
    private BigDecimal totalEarnings;

    @Schema(description = "实得收入（税后） ")
    private BigDecimal netPay;

    @Schema(description = "普通工时 ")
    private BigDecimal ordinaryHours;

    @Schema(description = "普通时薪")
    private BigDecimal ordinaryHoursHourlyRate;

    @Schema(description = "普通工时薪资（本次）")
    private BigDecimal ordinaryHoursThisPay;

    @Schema(description = "普通工时薪资（年）")
    private BigDecimal ordinaryHoursYtd;

    @Schema(description = "6点后工时")
    private BigDecimal after6pmHours;

    @Schema(description = "6点后时薪")
    private BigDecimal after6pmHoursHourlyRate;

    @Schema(description = "6点后工时薪资（本次）")
    private BigDecimal after6pmHoursThisPay;

    @Schema(description = "6点后工时薪资（年）")
    private BigDecimal after6pmHoursYtd;

    @Schema(description = "周六工时")
    private BigDecimal satHours;

    @Schema(description = "周六时薪")
    private BigDecimal satHoursHourlyRate;

    @Schema(description = "周六工时薪资（本次）")
    private BigDecimal satHoursThisPay;

    @Schema(description = "周六工时薪资（年）")
    private BigDecimal satHoursYtd;

    @Schema(description = "总收入（税前） ")
    private BigDecimal grossPay;

    @Schema(description = "年总收入（税前） ")
    private BigDecimal grossPayYtd;

    @Schema(description = "税额（PAYG） ")
    private BigDecimal tax;

    @Schema(description = "总税（年）")
    private BigDecimal taxYtd;

    @Schema(description = "养老金（SGC） ")
    private BigDecimal superannuation;

    @Schema(description = "年养老金（SGC）")
    private BigDecimal superYtd;

    @Schema(description = "银行转账参考信息 ")
    private String bankRef;

    @Schema(description = "银行账单名称显示 ")
    private String referenceName;

    @Schema(description = "银行支付金额")
    private BigDecimal payAmount;

    @Schema(description = "是否确认")
    private Byte ifCheck;

    @Schema(description = "备注")
    private String notes;

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