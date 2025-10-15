package com.hooper.ugg.common;

import lombok.Getter;

@Getter
public enum ResponseCode {

    SUCCESS(200, "success"),
    PARAM_ERROR(400, "parameter error"),
    FAIL(500, "fail"),
    INSERT_FAILED(5001, "insert fail"),
    UPDATE_FAILED(5002, "update fail"),
    DELETE_FAILED(5003, "delete fail"),

    USER_NOT_REGISTERED(1001, "请先注册用户信息"),
    ALREADY_UPLOADED(1004, "该工资单已上传"),
    INVALID_PAYSLIP(1002, "工资单解析失败"),
    FILE_EMPTY(1003, "上传的文件为空"),
    UNKNOWN_ERROR(9999, "系统发生未知错误");

    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
